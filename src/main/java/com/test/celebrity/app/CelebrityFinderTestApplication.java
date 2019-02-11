package com.test.celebrity.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * CelebrityTestApplication
 *
 * @author christian.valencia
 * @since 11/02/2019
 */
@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {"com.test.celebrity"})
public class CelebrityFinderTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CelebrityFinderTestApplication.class, args);
	}
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();

	}

}

