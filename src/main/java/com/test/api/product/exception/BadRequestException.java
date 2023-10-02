/**
 * 
 */
package com.test.api.product.exception;

import com.test.api.product.model.ApiError;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * DNFException.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 29, 2023 11:09:06 PM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
@Getter
@Setter
public class BadRequestException extends RuntimeException {
	
	private static final long serialVersionUID = -2511785669132429394L;
	
	private ApiError error;

	/**
	 * 
	 * @param message
	 */
	public BadRequestException(ApiError error) {
		super();
		this.error = error;
	}

	
}
