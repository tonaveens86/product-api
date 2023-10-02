/**
 * 
 */
package com.test.api.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * Message.java.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 30, 2023 3:42:40 AM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
@Data
@AllArgsConstructor
public class Message {

	private String code;
	private String field;
	private String message;
}
