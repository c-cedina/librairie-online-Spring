package com.example.librairie_online.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfigurationSecurityApplication {
    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.POST, "Client").permitAll()
                        .requestMatchers(HttpMethod.POST, "Client/Validate").permitAll()
                        .requestMatchers(HttpMethod.GET, "Manga").permitAll()
                        .requestMatchers("/Admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                ).build();
    }
}
