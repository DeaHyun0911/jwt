package com.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.addSecurityItem(new SecurityRequirement().addList(("Bearer Authentication")))
			.components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
			.info(apiInfo());
	}

	private SecurityScheme createAPIKeyScheme() {
		return new SecurityScheme().type(SecurityScheme.Type.HTTP)
			.bearerFormat("JWT")
			.scheme("bearer");
	}

	private Info apiInfo() {
		return new Info()
			.title("JWT 인증 프로젝트")
			.description("바로인턴 JWT 인증/인가 프로젝트")
			.version("1.0");
	}
}
