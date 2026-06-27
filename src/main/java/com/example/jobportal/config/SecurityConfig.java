package com.example.jobportal.config;

import com.example.jobportal.exception.CustomAccessDeniedHandler;
import com.example.jobportal.security.JwtFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(JwtFilter jwtFilter,
                          CustomAccessDeniedHandler accessDeniedHandler) {
        this.jwtFilter = jwtFilter;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth

                // PUBLIC
                .requestMatchers("/api/auth/**").permitAll()

                // SWAGGER
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // ALLOW OPTIONS (IMPORTANT FIX)
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // ROLE BASED
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/recruiter/**").hasRole("RECRUITER")
                .requestMatchers("/api/candidate/**").hasRole("CANDIDATE")

                .anyRequest().authenticated()
            )

            .exceptionHandling(ex ->
                ex.accessDeniedHandler(accessDeniedHandler)
            )

            .addFilterBefore(jwtFilter,
                    UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    // =========================
    // CORS CONFIG
    // =========================
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }
}