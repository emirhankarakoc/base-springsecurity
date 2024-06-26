package com.karakoc.security_demosu.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {


    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        http.
                cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(formlogin -> formlogin.disable())
                //bu alttaki, herhangi bir endpointe cors, formlogin,csrf,sessionmanagementi disable yapacaktir.
                .securityMatcher("/**")
                .authorizeHttpRequests(registry -> registry
                                                    .requestMatchers("/").permitAll()
                                                    .requestMatchers("/auth/login").permitAll()
                                                    .anyRequest().authenticated()
                                                    
            );
                
        return http.build();
    }
    
}
