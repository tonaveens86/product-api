/**
 * 
 */
package com.test.api.product.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * BeanLocator.java.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 30, 2023 3:36:56 AM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
@Component
public class BeanLocator implements ApplicationContextAware {

	private static ApplicationContext context;
	
	public static <T> T getBean(Class<T> clazz) {
		if(context != null) {
			return context.getBean(clazz);
		}
		return null;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

}
