package com.example.bff.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Permitir el origen del frontend
        config.addAllowedOrigin("http://localhost:4200"); // Cambia por la URL de tu frontend
        config.addAllowedOrigin("https://mi-dominio-produccion.com"); // Si aplica

        // Permitir métodos HTTP
        config.addAllowedMethod("*");

        // Permitir todas las cabeceras
        config.addAllowedHeader("*");

        // Habilitar credenciales
        config.setAllowCredentials(true);

        // Aplicar configuración a todas las rutas
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
