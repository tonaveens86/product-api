/**
 * 
 */
package com.test.api.product.controller;

import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;

import com.test.api.product.exception.BadRequestException;
import com.test.api.product.model.ApiRequest;
import com.test.api.product.model.ServiceRequest;
import com.test.api.product.model.ServiceResponse;
import com.test.api.product.util.ValidationFailureResponseBuilder;

/**
 * 
 * BaseRestController.java.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *          <BR>
 *          <B> Revision History: </B>
 *          <UL>
 *          <LI>Sep 30, 2023 2:11:06 AM (Naveen) Baseline</LI>
 *          </UL>
 *
 */
public interface BaseRestController {

	static final Logger LOGGER = LoggerFactory.getLogger(BaseRestController.class);

	default <T, P, R> R invokeService(ApiRequest apiReq, BindingResult bindingResult,
			BiFunction<T, ServiceRequest<P>, ServiceResponse<R>> invoker, ServiceRequest<P> serReq, T t) {
		LOGGER.debug("Started >>");
		if (bindingResult.hasErrors()) {
			var apiErrors = ValidationFailureResponseBuilder.buildResponse(bindingResult.getFieldErrors());
			LOGGER.debug("Validation failed..");
			throw new BadRequestException(apiErrors);
		}
		var res = invoker.apply(t, serReq).getPayload();
		LOGGER.debug("Ended >>");
		return res;
	}
}
