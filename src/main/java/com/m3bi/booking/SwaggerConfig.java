package com.m3bi.booking;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	public static final String DEFAULT_CONTACT= "Dhirendra Pratap Singh dp023.1994@gmail.com";
	public static final ApiInfo DEFAULT_API_INFO
    = new ApiInfo("Hotel Booking API Documentation","Custom Hotel Booking Documentation","1.0","Terms of Service",DEFAULT_CONTACT,"Apache 2.0","http://www.apache.org/licenses/LICENSE-2.0");
	private static final Set<String> DEFAULT_PRODUCES_CONSUME = new HashSet<String>(Arrays.asList("application/json","application/xml"));

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(DEFAULT_API_INFO)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.m3bi.booking"))
				.build();
	}
}
