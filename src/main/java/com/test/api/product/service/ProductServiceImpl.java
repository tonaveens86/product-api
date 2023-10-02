/**
 * 
 */
package com.test.api.product.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.test.api.product.entity.ApprovalQueue;
import com.test.api.product.entity.Product;
import com.test.api.product.exception.BusinessException;
import com.test.api.product.exception.DNFException;
import com.test.api.product.model.ProductRequest;
import com.test.api.product.model.ServiceRequest;
import com.test.api.product.model.ServiceResponse;
import com.test.api.product.repo.ApprovalQueueRepository;
import com.test.api.product.repo.ProductRepository;
import com.test.api.product.util.ServiceResponseHelper;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * ProductServiceImpl.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *          <BR>
 *          <B> Revision History: </B>
 *          <UL>
 *          <LI>Sep 29, 2023 11:14:41 PM (Naveen) Baseline</LI>
 *          </UL>
 *
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductServiceI {

	@Autowired
	private ServiceResponseHelper helper;

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private ApprovalQueueRepository approvalQueueRepo;

	@Autowired
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	@Override
	public ServiceResponse<List<Product>> findActiveProducts(ServiceRequest<?> req) {
		log.debug("Started >>");
		var list = this.productRepo.findActiveProducts();
		log.debug("Ended >>");
		return this.helper.buildResponse(list);
	}

	@Transactional(rollbackFor = Throwable.class)
	@Override
	public ServiceResponse<Product> deleteById(ServiceRequest<Long> req) {
		log.debug("Started >>");
		this.productRepo.deleteById(req.getPayload());
		var product = new Product();
		product.setProductId(req.getPayload());
		log.debug("Ended >>");
		return this.helper.buildResponse(product);
	}

	@Transactional(readOnly = true)
	@Override
	public ServiceResponse<List<Product>> search(ServiceRequest<ProductRequest> req) {
		log.debug("Started >>");
		var payload = req.getPayload();
		var query = new StringBuilder("select p from Product p where ");
		var queryExeFlg = false;
		var prodNameFlg = false;
		var priceFlg = false;
		var dateFlg = false;
		if(StringUtils.hasLength(payload.getProductName())) {
			query.append(" (UPPER(t.name) = UPPER(:productName))");
			queryExeFlg = true;
			
			prodNameFlg = true;
		}
		if(null != payload.getMinPrice() && null != payload.getMaxPrice()) {
			if(queryExeFlg) {
				query.append(" AND ");
			}
			query.append(" (p.price >= :minPrice and p.price <= :maxPrice) ");
			queryExeFlg = true;
			
			priceFlg = true;
		}
		if(null != payload.getMinPostedDate() && null != payload.getMaxPostedDate()) {
			if(queryExeFlg) {
				query.append(" AND ");
			}
			query.append(" (p.createdDate >= :minDate and p.createdDate <= :maxDate) ");
			queryExeFlg = true;
			
			dateFlg = true;
		}
		if(!queryExeFlg) {
			throw new BusinessException(30001, "Invalid search criteria");
		}
		var typedQuery = this.entityManager.createQuery(query.toString(), Product.class);
		if(prodNameFlg) {
			typedQuery.setParameter("productName", payload.getProductName());
		}
		if(priceFlg) {
			typedQuery.setParameter("minPrice", payload.getMinPrice());
			typedQuery.setParameter("maxPrice", payload.getMaxPrice());
		}
		if(dateFlg) {
			typedQuery.setParameter("minDate", payload.getMinPostedDate());
			typedQuery.setParameter("maxDate", payload.getMaxPostedDate());
		}
		var list = typedQuery.getResultList();
		log.debug("Ended >>");
		return this.helper.buildResponse(list);
	}

	@Transactional(readOnly = true)
	@Override
	public ServiceResponse<Product> findById(ServiceRequest<Long> req) {
		log.debug("Started >>");
		var product = this.productRepo.findById(req.getPayload());
		log.debug("Ended >>");
		return this.helper.buildResponse(product);
	}

	@Transactional(rollbackFor = Throwable.class)
	@Override
	public ServiceResponse<Product> update(ServiceRequest<Product> req) {
		log.debug("Started >>");
		var payload = req.getPayload();
		payload.setStatus("APPROVED");
		if (payload.getPrice().compareTo(new BigDecimal(10000)) > 0) {
			throw new BusinessException(20001, "Product price must not exceed $10,000");
		}
		var productId = req.getParams().get("productId");
		if (StringUtils.hasLength(productId)) {
			// Update flow
			var opt = this.productRepo.findById(Long.valueOf(productId));
			if (opt.isEmpty()) {
				throw new DNFException("Prodcut data not found with given prodcut Id: " + productId);
			}
			var dbProduct = opt.get();
			if (dbProduct.getPrice().compareTo(new BigDecimal(10000)) > 0) {
				throw new BusinessException(20001, "Product price must not exceed $10,000");
			}
			var prvPriceDiv = dbProduct.getPrice().divide(new BigDecimal(2));
			var newPriceMax = dbProduct.getPrice().add(prvPriceDiv);
			if (payload.getPrice().compareTo(newPriceMax) > 0) {
				payload.setStatus("SENT_FOR_APPROVAL");
			}
			payload.setProductId(Long.valueOf(productId));
		} else {
			// Create flow
			if (payload.getPrice().compareTo(new BigDecimal(5000)) > 0) {
				payload.setStatus("SENT_FOR_APPROVAL");
			}
		}

		var product = this.productRepo.save(payload);
		if(product.getStatus() == "SENT_FOR_APPROVAL") {
			var queue = new ApprovalQueue();
			queue.setProduct(product);
			this.approvalQueueRepo.save(queue);
		}
		log.debug("Ended >>");
		return this.helper.buildResponse(product);
	}

	@Transactional(readOnly = true)
	@Override
	public ServiceResponse<List<ApprovalQueue>> getApprovalQueueProducts(ServiceRequest<?> req) {
		log.debug("Started >>");
		List<ApprovalQueue> list = new ArrayList<>();
		this.approvalQueueRepo.findAllByOrderByCreatedDateAsc().forEach(list::add);
		log.debug("Ended >>");
		return this.helper.buildResponse(list);
	}

	@Override
	public ServiceResponse<Product> approveProduct(ServiceRequest<ApprovalQueue> req) {
		return handelApprovalState(req, "APPROVED");
	}

	@Override
	public ServiceResponse<Product> rejectProduct(ServiceRequest<ApprovalQueue> req) {
		return handelApprovalState(req, "REJECTED");
	}

	/**
	 * @param req
	 * @return
	 */
	@Transactional(rollbackFor = Throwable.class)
	public ServiceResponse<Product> handelApprovalState(ServiceRequest<ApprovalQueue> req, String status) {
		log.debug("Started >>");
		var payload = req.getPayload();
		var approvalId = payload.getApprovalId();
		var productId = payload.getProduct().getProductId();
		this.approvalQueueRepo.deleteById(approvalId);
		var opt = this.productRepo.findById(productId);
		if (opt.isEmpty()) {
			throw new DNFException("Prodcut data not found with given prodcut Id: " + productId);
		}
		var prd = opt.get();
		prd.setStatus(status);
		log.debug("Ended >>");
		return this.helper.buildResponse(this.productRepo.save(prd));
	}

}
