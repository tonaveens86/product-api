/**
 * 
 */
package com.test.api.product.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.test.api.product.model.Message;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * ValidationMessages.java.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 30, 2023 3:36:39 AM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
@Configuration
@ConfigurationProperties(prefix = "validation.messages")
@Setter
@Getter
public class ValidationMessages {
	
	private Map<String, Message> errors = new HashMap<>();
	private Map<String, Message> info = new HashMap<>();
	private Map<String, Message> warning = new HashMap<>();
}
