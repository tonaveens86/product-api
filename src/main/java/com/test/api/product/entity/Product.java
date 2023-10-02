/**
 * 
 */
package com.test.api.product.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.test.api.product.model.ApiRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Product.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 29, 2023 5:54:23 PM (Naveen) Baseline</LI>
 *      </UL>
 * 
 */
@Entity
@Table(name = "PRODUCT")
@Getter
@Setter
public class Product extends ApiRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PRODUCT_ID")
	private Long productId;
	
	@NotBlank(message = "20002")
	@Column(name = "PRODUCT_NAME", length = 50, nullable = false, unique = false)
	private String name;
	
	@DecimalMax(value = "10000", inclusive = false, message = "20001")
	@Column(name = "PRICE", nullable = false)
	private BigDecimal price;
	
	@Column(name = "STATUS", nullable = false)
	private String status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate = new Date();
	
}
