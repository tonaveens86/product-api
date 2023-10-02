/**
 * 
 */
package com.test.api.product.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.test.api.product.entity.Product;

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
public interface ProductRepository extends CrudRepository<Product, Long> {

	@Query("from Product p where p.status = 'APPROVED' ORDER BY p.createdDate DESC")
	List<Product> findActiveProducts();
}
