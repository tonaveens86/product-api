/**
 * 
 */
package com.test.api.product.entity;

import java.util.Date;

import com.test.api.product.model.ApiRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * ApprovalQueue.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *          <BR>
 *          <B> Revision History: </B>
 *          <UL>
 *          <LI>Sep 30, 2023 12:00:12 AM (Naveen) Baseline</LI>
 *          </UL>
 *
 */
@Table(name = "APPROVAL_QUEUE")
@Entity
@Getter
@Setter
public class ApprovalQueue extends ApiRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "APPROVAL_ID")
	private Long approvalId;

	@NotNull(message = "20003")
	@OneToOne
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;

	@Column(name = "CREATED_DATE")
	private Date createdDate = new Date();
}
