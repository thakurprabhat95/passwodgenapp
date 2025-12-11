package com.passwordgen.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        
        registry.addMapping("/**") // Apply these CORS rules to ALL endpoints ("/**")
                .allowedOrigins("https://dazzling-starlight-ad6e64.netlify.app/", "http://localhost:9090") // List the origins (frontend domains) allowed to access your backend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // List the HTTP methods allowed
                .allowedHeaders("*") // Allow all request headers
                .allowCredentials(true) // Allow cookies, authorization headers, and client certificates
                .maxAge(3600); // Set max age for pre-flight cache (in seconds)
    }

}
