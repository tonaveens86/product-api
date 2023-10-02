/**
 * 
 */
package com.test.api.product.util;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.test.api.product.exception.DNFException;
import com.test.api.product.model.ServiceResponse;

/**
 * 
 * ServiceResponseHelper.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 29, 2023 11:16:06 PM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
@Component
public class ServiceResponseHelper {

	
	public <T> ServiceResponse<T> buildResponse(T resPayload) {
		var opt = Optional.ofNullable(resPayload);
		return buildResponse(opt);
	}
	
	public <T> ServiceResponse<T> buildResponse(Optional<T> opt) {
		if(opt.isEmpty()) {
			throw new DNFException();
		}
		return new ServiceResponse<>(opt.get());
	}
	
}