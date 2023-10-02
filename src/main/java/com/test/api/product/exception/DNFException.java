/**
 * 
 */
package com.test.api.product.exception;

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
public class DNFException extends RuntimeException {
	
	private static final long serialVersionUID = -2511785669132429394L;
	
	private int code;
	private String message;

	/**
	 * 
	 */
	public DNFException() {
		super();
		this.code = 404;
		this.message = "Data Not Found.";
	}
	
	/**
	 * 
	 * @param message
	 */
	public DNFException(String message) {
		super(message);
		this.code = 404;
		this.message = message;
	}

	
}
