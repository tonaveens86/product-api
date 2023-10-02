/**
 * 
 */
package com.test.api.product.config;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

/**
 * 
 * CustomHttpServeletWrapper.java.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 30, 2023 9:03:18 PM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
public class CustomHttpServeletWrapper extends HttpServletRequestWrapper {

	private final Map<String, String[]> additionalParams;
	private Map<String, String[]> allParams;
	
	/**
	 * @param request
	 */
	public CustomHttpServeletWrapper(HttpServletRequest request, final Map<String, String[]> additionalParams) {
		super(request);
		this.additionalParams = new TreeMap<>();
		this.additionalParams.putAll(additionalParams); 
	}

	/**
	 * @see jakarta.servlet.ServletRequestWrapper#getParameter(java.lang.String)
	 */
	@Override
	public String getParameter(String name) {
		return super.getParameter(name);
	}

	/**
	 * @see jakarta.servlet.ServletRequestWrapper#getParameterMap()
	 */
	@Override
	public Map<String, String[]> getParameterMap() {
		if(allParams == null) {
			this.allParams = new TreeMap<>();
			this.allParams.putAll(super.getParameterMap());
			this.allParams.putAll(this.additionalParams);
		}
		return Collections.unmodifiableMap(this.allParams);
	}

	/**
	 * @see jakarta.servlet.ServletRequestWrapper#getParameterNames()
	 */
	@Override
	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(this.getParameterMap().keySet());
	}

	/**
	 * @see jakarta.servlet.ServletRequestWrapper#getParameterValues(java.lang.String)
	 */
	@Override
	public String[] getParameterValues(String name) {
		return this.getParameterMap().get(name);
	}
	
	

}
