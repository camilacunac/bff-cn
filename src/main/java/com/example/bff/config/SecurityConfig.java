package com.example.bff.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.cors(Customizer.withDefaults())
				.csrf(csrf -> csrf.ignoringRequestMatchers("/ws/alertas")) // Desactiva CSRF solo para WebSocket
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/ws/alertas").permitAll() // Permitir acceso público a WebSocket
						.anyRequest().authenticated() // Requerir autenticación para todo lo demás
				)
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

		return http.build();
	}

}
