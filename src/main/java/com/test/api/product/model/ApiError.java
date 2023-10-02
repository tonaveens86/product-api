/**
 * 
 */
package com.test.api.product.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * ApiError.java.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 30, 2023 3:41:26 AM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
@Setter
@Getter
public class ApiError {

	private int status;
	private String statusDescription;
	private List<Message> errors = new ArrayList<>();
	private String traceId;
	
	public void addMessage(Message msg) {
		this.errors.add(msg);
	}
}
