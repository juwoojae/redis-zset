package com.example.rediszset.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Redis ZSet API",
                version = "v1",
                description = "API documentation for Redis ZSet examples."
        )
)
public class OpenApiConfig {
}
