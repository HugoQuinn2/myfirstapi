package com.hq.myfirtsapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> //Deshabilitar la autenticacion con csrf no se utilizara
                        csrf.
                                disable())
                .authorizeHttpRequests(authRequest -> //Filtro para rutas protegidas
                        authRequest
                                .requestMatchers("/auth/**").permitAll() //Todos los request los cuales tengan auth seran publico y no ocuparan autenticacion
                                .anyRequest().authenticated()
                )
                .formLogin(withDefaults()) //Formulario de login por defecto de spring security
                .build();
    }

}
