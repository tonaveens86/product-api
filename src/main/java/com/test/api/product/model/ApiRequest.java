/**
 * 
 */
package com.test.api.product.model;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * ApiRequest.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 29, 2023 11:47:40 PM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
@Getter
@Setter
public class ApiRequest {
	
	private static final String CONSUMER_KEY = "consumerKey";

	@JsonIgnore
	private String traceId;
	@JsonIgnore
	private String consumerKey;
	@JsonIgnore
	private String xUserId;
	
	public void validate(Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, CONSUMER_KEY, "100001", "X-CONSUMER-KEY can't be null or empty");
	}
	
	public void bindProps(ApiRequest req) {
		this.traceId = req.traceId;
		this.consumerKey = req.getConsumerKey();
	}
	
}
