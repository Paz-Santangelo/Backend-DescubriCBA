package com.final_project.descubri_cba.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
        registry.addMapping("/**") // Aplica CORS a todos los endpoints de la API
                .allowedOriginPatterns("*") // Permite cualquier origen (localhost, IPs locales, etc.)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") // Métodos HTTP permitidos
                .allowedHeaders("*") // Permite todos los headers en las peticiones
                .allowCredentials(true) // Permite el envío de cookies y credenciales
                .maxAge(3600); // Cache de preflight por 1 hora (3600 segundos)
    }
}
