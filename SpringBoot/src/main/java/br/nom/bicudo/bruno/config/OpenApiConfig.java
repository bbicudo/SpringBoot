package br.nom.bicudo.bruno.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
		return new OpenAPI()
			.info(
				new Info()
					.title("Restful API with Java 23 and Spring Boot 3.4.1")
					.version("v1")
					.description("Some Description.")
					.termsOfService("https://github.com/bbicudo/SpringBoot/blob/main/README.md")
					.license(
						new License().name("The Unlicense").url("https://unlicense.org")
					)
			);
	}
}
