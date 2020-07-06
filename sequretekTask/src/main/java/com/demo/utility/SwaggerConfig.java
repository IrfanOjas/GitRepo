package com.demo.utility;
import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {                
	    return new Docket(DocumentationType.SWAGGER_2)          
	    		.select()
                .apis(RequestHandlerSelectors.basePackage("com.demo"))
                .paths(regex("/.*"))
                .build()
               .apiInfo(metaData());
	}
	
	private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Sequretek Service",
                "",
                "",
                "",
                new Contact("", "",""),
                "",
                "");
        return apiInfo;
    }
}