/**
 * 
 */
package com.test.api.product.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * BusinessException.java.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 30, 2023 1:46:24 AM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
@Setter
@Getter
public class BusinessException extends RuntimeException {
	
	private static final long serialVersionUID = -7134098473518863223L;
	
	private int code;
	private String message;
	
	/**
	 * 
	 * @param message
	 */
	public BusinessException(int code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	
}
