/**
 * 
 */
package com.test.api.product.config;

import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * LogInterceptor.java.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 30, 2023 7:23:36 PM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
@Component
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

	/**
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(jakarta.servlet.http.HttpServletRequest, jakarta.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		MDC.clear();
		var traceId = request.getParameter("traceId");
		if(!StringUtils.hasLength(traceId)) {
			traceId = UUID.randomUUID().toString();
		}
		var consumerId = request.getParameter("consumerKey");
		
		MDC.put("X-TRACE-ID", traceId);
		MDC.put("X-CONSUMER-KEY", consumerId);
		MDC.put("OPERATION_NAME", request.getRequestURI() + " - " + request.getMethod());
		
		var st = System.currentTimeMillis();
		MDC.put("OPERATION_START_TIME", String.valueOf(st));
		log.debug("Rest API [{} - {}] execution ended at{}", request.getRequestURI(), request.getMethod(), String.valueOf(st));
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	/**
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(jakarta.servlet.http.HttpServletRequest, jakarta.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		var st = Long.parseLong(MDC.get("OPERATION_START_TIME"));
		var et = System.currentTimeMillis();
		var t = et - st;
		MDC.put("OPERATION_SLA", String.valueOf(t));
		log.debug("Rest API [{} - {}] execution ended at {}", request.getRequestURI(), request.getMethod(), String.valueOf(et));
		log.debug("Rest API [{} - {}] total execution time(SLA): {} msec", request.getRequestURI(), request.getMethod(), String.valueOf(t));
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	/**
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(jakarta.servlet.http.HttpServletRequest, jakarta.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		MDC.clear();
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

	
}
