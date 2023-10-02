/**
 * 
 */
package com.test.api.product.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * CusterFilter.java.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *          <BR>
 *          <B> Revision History: </B>
 *          <UL>
 *          <LI>Sep 30, 2023 8:52:46 PM (Naveen) Baseline</LI>
 *          </UL>
 *
 */
@Component
public class CusterFilter implements Filter {

	/**
	 * @see jakarta.servlet.Filter#doFilter(jakarta.servlet.ServletRequest,
	 *      jakarta.servlet.ServletResponse, jakarta.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest serReq, ServletResponse serRes, FilterChain chain)
			throws IOException, ServletException {
		var request = (HttpServletRequest) serReq;
		var traceId = request.getHeader("X-TRACE-ID") != null ? request.getHeader("X-TRACE-ID")
				: request.getParameter("X-TRACE-ID");
		if (!StringUtils.hasLength(traceId)) {
			traceId = UUID.randomUUID().toString();
		}
		var consumerId = request.getHeader("X-CONSUMER-KEY") != null ? request.getHeader("X-CONSUMER-KEY")
				: request.getParameter("X-CONSUMER-KEY");
		Map<String, String[]> params = new HashMap<>();
		params.put("traceId", new String[] { traceId });
		params.put("consumerKey", new String[] { consumerId });
		chain.doFilter(new CustomHttpServeletWrapper(request, params), serRes);
	}

}
