package com.laporeon.uploader.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Uploader API")
                                .version("1.0.0")
                                .description("""
                                    A simple REST API built for learning how to handle file uploads and downloads with Spring Boot.
                                    
                                    **Key features:**
                                    
                                    - Supports upload, download, listing and deletion of files.
                                    - Validates file name and extension against custom patterns.
                                    - Automatically detects content type on download.
                                    """)
                                .license(
                                        new License()
                                                .name("MIT")
                                                .url("https://opensource.org/licenses/MIT")
                                )
                );
    }
}