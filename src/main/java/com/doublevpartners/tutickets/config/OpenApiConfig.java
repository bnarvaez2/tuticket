package com.doublevpartners.tutickets.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
  info = @Info(
    title = "Ticket Management API - TuTicket",
    version = "1.0.0",
    description = "API documentation for managing users and tickets"
  )
)
public class OpenApiConfig {
}

