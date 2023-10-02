/**
 * 
 */
package com.test.api.product.service;

import java.util.List;

import com.test.api.product.entity.ApprovalQueue;
import com.test.api.product.entity.Product;
import com.test.api.product.model.ProductRequest;
import com.test.api.product.model.ServiceRequest;
import com.test.api.product.model.ServiceResponse;

/**
 * 
 * ProductServiceI.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 29, 2023 11:06:39 PM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
public interface ProductServiceI {

	public ServiceResponse<List<Product>> findActiveProducts(ServiceRequest<?> req);
	public ServiceResponse<Product> deleteById(ServiceRequest<Long> req);
	public ServiceResponse<List<Product>> search(ServiceRequest<ProductRequest> req);
	public ServiceResponse<Product> findById(ServiceRequest<Long> req);
	public ServiceResponse<Product> update(ServiceRequest<Product> req);
	
	public ServiceResponse<List<ApprovalQueue>> getApprovalQueueProducts(ServiceRequest<?> req);
	public ServiceResponse<Product> approveProduct(ServiceRequest<ApprovalQueue> req);
	public ServiceResponse<Product> rejectProduct(ServiceRequest<ApprovalQueue> req);
}
