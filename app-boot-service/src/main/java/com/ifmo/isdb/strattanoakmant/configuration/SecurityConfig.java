package com.ifmo.isdb.strattanoakmant.configuration;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/api/**").allowedOrigins("*").allowedMethods("*");
                // Разрешаем доступ к Swagger UI
                registry.addMapping("/swagger-ui/**").allowedOrigins("*").allowedMethods("*");
                registry.addMapping("/swagger-resources/**").allowedOrigins("*").allowedMethods("*");
                registry.addMapping("/v2/api-docs").allowedOrigins("*").allowedMethods("*");
            }
        };
    }
}
