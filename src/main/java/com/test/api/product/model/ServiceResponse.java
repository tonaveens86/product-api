/**
 * 
 */
package com.test.api.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * ServiceResponse.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 29, 2023 11:07:44 PM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
@Data
@AllArgsConstructor
public class ServiceResponse<T> {

	private T payload;
	
}
