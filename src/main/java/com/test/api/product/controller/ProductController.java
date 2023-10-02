/**
 * 
 */
package com.test.api.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.api.product.entity.ApprovalQueue;
import com.test.api.product.entity.Product;
import com.test.api.product.model.ApiRequest;
import com.test.api.product.model.ProductRequest;
import com.test.api.product.model.ServiceRequest;
import com.test.api.product.service.ProductServiceI;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

/**
 * 
 * ProductController.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *          <BR>
 *          <B> Revision History: </B>
 *          <UL>
 *          <LI>Sep 29, 2023 5:48:29 PM (Naveen) Baseline</LI>
 *          </UL>
 *
 */
@RestController
@RequestMapping("/api/products")
public class ProductController implements BaseRestController {

	@Autowired
	private ProductServiceI service;

	@GetMapping
	public List<Product> findActiveProducts(@Parameter(hidden = true) ApiRequest request, BindingResult bindingResult) {
		request.validate(bindingResult);
		return this.invokeService(request, bindingResult, (t, sr) -> t.findActiveProducts(sr), new ServiceRequest<>(),
				this.service);
	}

	@DeleteMapping("/{productId}")
	public Product deleteById(@PathVariable("productId") Long productId, @Parameter(hidden = true) ApiRequest request,
			BindingResult bindingResult) {
		request.validate(bindingResult);
		var serReq = new ServiceRequest<>(productId);
		return this.invokeService(request, bindingResult, (t, sr) -> t.deleteById(sr), serReq, this.service);
	}

	@GetMapping("/search")
	public List<Product> search(@Parameter(hidden = true) ProductRequest request, BindingResult bindingResult) {
		request.validate(bindingResult);
		var serReq = new ServiceRequest<>(request);
		return this.invokeService(request, bindingResult, (t, sr) -> t.search(sr), serReq, this.service);
	}

	@GetMapping("/{productId}")
	public Product findById(@PathVariable("productId") Long productId, @Parameter(hidden = true) ApiRequest request,
			BindingResult bindingResult) {
		request.validate(bindingResult);
		var serReq = new ServiceRequest<>(productId);
		return this.invokeService(request, bindingResult, (t, sr) -> t.findById(sr), serReq, this.service);
	}

	@PostMapping
	public Product create(@Parameter(hidden = true) ApiRequest apiReq, @RequestBody @Valid Product reqPayload,
			BindingResult bindingResult) {
		reqPayload.bindProps(apiReq);
		reqPayload.validate(bindingResult);
		var serReq = new ServiceRequest<>(reqPayload);
		return this.invokeService(apiReq, bindingResult, (t, sr) -> t.update(sr), serReq, this.service);
	}

	@PutMapping("/{productId}")
	public Product update(@PathVariable("productId") Long productId, @Parameter(hidden = true) ApiRequest apiReq,
			@RequestBody @Valid Product reqPayload, BindingResult bindingResult) {
		reqPayload.bindProps(apiReq);
		reqPayload.validate(bindingResult);
		var serReq = new ServiceRequest<>(reqPayload);
		serReq.getParams().put("productId", productId.toString());
		return this.invokeService(apiReq, bindingResult, (t, sr) -> t.update(sr), serReq, this.service);
	}

	@GetMapping("/approval-queue")
	public List<ApprovalQueue> getApprovalQueueProducts(@Parameter(hidden = true) ApiRequest apiReq,
			BindingResult bindingResult) {
		apiReq.validate(bindingResult);
		var serReq = new ServiceRequest<>();
		return this.invokeService(apiReq, bindingResult, (t, sr) -> t.getApprovalQueueProducts(sr), serReq,
				this.service);
	}

	@PutMapping("/approval-queue/{approvalId}/approve")
	public Product approveProduct(@PathVariable("approvalId") Long approvalId,
			@Parameter(hidden = true) ApiRequest apiReq, @RequestBody @Valid ApprovalQueue payload,
			BindingResult bindingResult) {
		payload.bindProps(apiReq);
		payload.validate(bindingResult);
		var serReq = new ServiceRequest<>(payload);
		serReq.getParams().put("approvalId", approvalId.toString());
		return this.invokeService(apiReq, bindingResult, (t, sr) -> t.approveProduct(sr), serReq, this.service);
	}

	@PutMapping("/approval-queue/{approvalId}/reject")
	public Product rejectProduct(@PathVariable("approvalId") Long approvalId,
			@Parameter(hidden = true) ApiRequest apiReq, @RequestBody @Valid ApprovalQueue payload,
			BindingResult bindingResult) {
		payload.bindProps(apiReq);
		payload.validate(bindingResult);
		var serReq = new ServiceRequest<>(payload);
		serReq.getParams().put("approvalId", approvalId.toString());
		return this.invokeService(apiReq, bindingResult, (t, sr) -> t.rejectProduct(sr), serReq, this.service);
	}

}
