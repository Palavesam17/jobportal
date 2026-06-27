package com.example.jobportal.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI jobPortalAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Job Portal API")
                        .version("1.0")
                        .description("Full Stack Job Portal Backend APIs"));
    }
}