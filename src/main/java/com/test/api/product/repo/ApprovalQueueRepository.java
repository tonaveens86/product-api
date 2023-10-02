/**
 * 
 */
package com.test.api.product.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.test.api.product.entity.ApprovalQueue;

/**
 * 
 * ProductRepository.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 29, 2023 10:51:43 PM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
@Repository
public interface ApprovalQueueRepository extends CrudRepository<ApprovalQueue, Long> {

	public List<ApprovalQueue> findAllByOrderByCreatedDateAsc(); 
}
