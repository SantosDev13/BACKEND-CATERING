package com.catering.backend.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Configurar quién puede entrar a dónde
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults()) // Habilitar CORS (React)
            .csrf(csrf -> csrf.disable())    // Deshabilitar CSRF para facilitar APIs
            .authorizeHttpRequests(auth -> auth
                // Rutas Públicas (Todo el mundo puede ver productos y cotizar)
                .requestMatchers("/api/products/**", "/api/quotes/**").permitAll()
                // Rutas Privadas (Solo Admin)
                .requestMatchers("/api/admin/**").authenticated()
                // Cualquier otra cosa
                .anyRequest().permitAll()
            )
            .httpBasic(Customizer.withDefaults()); // Usaremos autenticación básica (Usuario/Pass en cabecera)

        return http.build();
    }

    // 2. Conectar Spring Security con tu Base de Datos MySQL
    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        // Consultas para buscar el usuario y su rol
        users.setUsersByUsernameQuery("SELECT username, password, 'true' as enabled FROM users WHERE username = ?");
        users.setAuthoritiesByUsernameQuery("SELECT username, role FROM users WHERE username = ?");
        return users;
    }
}