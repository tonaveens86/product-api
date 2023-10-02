/**
 * 
 */
package com.test.api.product.exception;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.test.api.product.model.ApiError;
import com.test.api.product.util.ValidationFailureResponseBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * GlobalExceptionHandler.java.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *          <BR>
 *          <B> Revision History: </B>
 *          <UL>
 *          <LI>Sep 30, 2023 4:11:25 AM (Naveen) Baseline</LI>
 *          </UL>
 *
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public final ResponseEntity<Object> handleException(MethodArgumentNotValidException ex, WebRequest request) {
		log.error("Exception: ", ex);
		return handleMethodArgumentNotValid((MethodArgumentNotValidException) ex, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ BusinessException.class })
	public final ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
		log.error("Exception: ", ex);
		var apiResponse = new ApiError();
		apiResponse.setStatus(ex.getCode());
		apiResponse.setStatusDescription(ex.getMessage());
		apiResponse.setTraceId(getTraceId(request));
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * @param request
	 * @return
	 */
	private String getTraceId(WebRequest request) {
		var traceId = StringUtils.hasLength(request.getParameter("traceId")) ? request.getParameter("traceId")
				: request.getHeader("X-TRACE-ID");
		if(!StringUtils.hasLength(traceId)) {
			traceId = MDC.get("X-TRACE-ID");
		}
		return traceId;
	}

	@ExceptionHandler({ DNFException.class })
	public final ResponseEntity<Object> handleDNFException(DNFException ex, WebRequest request) {
		log.error("Exception: ", ex);
		var apiResponse = new ApiError();
		apiResponse.setStatus(ex.getCode());
		apiResponse.setStatusDescription(ex.getMessage());
		apiResponse.setTraceId(getTraceId(request));
		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ BadRequestException.class })
	public final ResponseEntity<Object> handleValidationFailure(BadRequestException ex, WebRequest request) {
		log.error("Exception: ", ex);
		var appErr = ex.getError();
		appErr.setTraceId(this.getTraceId(request));
		return new ResponseEntity<>(appErr, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ Throwable.class })
	public final ResponseEntity<Object> handleGenericException(Throwable ex, WebRequest request) {
		log.error("Exception: ", ex);
		var apiResponse = new ApiError();
		apiResponse.setStatus(500);
		apiResponse.setStatusDescription(ex.getMessage());
		apiResponse.setTraceId(getTraceId(request));
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpStatus status,
			WebRequest request) {
		var apiErrors = ValidationFailureResponseBuilder.buildResponse(ex.getFieldErrors());
		return new ResponseEntity<>(apiErrors, status);
	}
}
