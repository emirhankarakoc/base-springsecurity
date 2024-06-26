package com.karakoc.security_demosu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.karakoc.security_demosu.model.LoginRequest;
import com.karakoc.security_demosu.model.LoginResponse;
import com.karakoc.security_demosu.security.JwtIssuer;

import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
public class AuthController {
    

    private final JwtIssuer jwtIssuer;

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        var token = jwtIssuer.issue(1L, request.getEmail(), List.of("USER"));
        return LoginResponse.builder()
                .accessToken(token)
                .build();        
    }

}
