/**
 * 
 */
package com.test.api.product.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * 
 * ServiceRequest.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 29, 2023 11:44:02 PM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
@Data
public class ServiceRequest<T> {

	private Map<String, String> params = new HashMap<>();
	private T payload;
	
	public ServiceRequest() {
		//
	}
	
	public ServiceRequest(T t) {
		this.payload = t;
	}
}
