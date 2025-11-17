package com.final_project.descubri_cba.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.final_project.descubri_cba.converter.StringToAccommodationTypeConverter;

/**
 * Configuración global de CORS para permitir peticiones desde cualquier frontend
 * durante el desarrollo local.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Configura las reglas de CORS de forma global para toda la aplicación.
     * Esta configuración permite el acceso desde cualquier origen durante el desarrollo.
     * 
     * @param registry Registro de configuraciones CORS
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") 
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToAccommodationTypeConverter());
    }
}
