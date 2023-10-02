/**
 * 
 */
package com.test.api.product.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * ProductRequest.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 29, 2023 11:47:00 PM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductRequest extends ApiRequest {

	private String productName;
	private BigDecimal minPrice;
	private BigDecimal maxPrice;
	private Date minPostedDate;
	private Date maxPostedDate;
}
