/**
 * 
 */
package com.test.api.product.config;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;

/**
 * 
 * OpenApiConfig.java.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 30, 2023 8:12:55 PM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
@Configuration
@OpenAPIDefinition
public class OpenApiConfig {

	@Bean
	public OperationCustomizer customParamConfig() {
		return (opr, hm) -> {
			opr.addParametersItem(getParam(ParameterIn.HEADER.toString(), "X-TRACE-ID", "Trace Id for debugging purpose - UUID", false));
			opr.addParametersItem(getParam(ParameterIn.HEADER.toString(), "X-CONSUMER-KEY", "Consumer key", true));
			return opr;
		};
	}

	private Parameter getParam(String inType, String name, String desc, boolean reqFlg) {
		return new Parameter().in(inType).schema(new StringSchema()).name(name).description(desc).required(reqFlg);
	}
}
