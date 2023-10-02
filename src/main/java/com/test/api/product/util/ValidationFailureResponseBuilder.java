/**
 * 
 */
package com.test.api.product.util;

import java.util.List;

import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;

import com.test.api.product.config.ValidationMessages;
import com.test.api.product.model.ApiError;
import com.test.api.product.model.Message;

/**
 * 
 * ValidationFailureResponseBuilder.java.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 30, 2023 3:35:25 AM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
public class ValidationFailureResponseBuilder {
	
	private static final String UNHANDLED_FIELD_ERROR_CODE = "11111";
	
	private static ValidationMessages messages;
	
	public static ApiError buildResponse(final List<FieldError> errors) {
		if(messages == null) {
			messages = BeanLocator.getBean(ValidationMessages.class);
		}
		var apiRes = new ApiError();
		apiRes.setStatus(400);
		apiRes.setStatusDescription("Validation Failure");
		errors.forEach(error -> {
			var errorCode = UNHANDLED_FIELD_ERROR_CODE;
			var msg = StringUtils.hasLength(error.getDefaultMessage()) ? error.getDefaultMessage() : "Validation failed on " + error.getField();
			var message = new Message(errorCode, error.getField(), msg);
			if(null != messages && null != messages.getErrors()) {
				var _errors = messages.getErrors();
				var _message = _errors.get(error.getDefaultMessage());
				if(null != _message)	{
					message = _message;
				}
			}
			apiRes.addMessage(message);
		});
		
		return apiRes;
	}
	
}
