/**
 * 
 */
package com.test.api.product.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 * BaseMvcConfigurer.java.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *          <BR>
 *          <B> Revision History: </B>
 *          <UL>
 *          <LI>Sep 30, 2023 7:45:58 PM (Naveen) Baseline</LI>
 *          </UL>
 *
 */
@Configuration
public class BaseMvcConfigurer implements WebMvcConfigurer {

	@Autowired
	private LogInterceptor logInterceptor;

	/**
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(this.logInterceptor).addPathPatterns("/api/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}

}
