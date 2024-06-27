package com.karakoc.sofra.controller;

import com.karakoc.sofra.model.RegisterRequest;
import com.karakoc.sofra.model.UserDTO;
import com.karakoc.sofra.security.UserPrincipal;
import com.karakoc.sofra.service.AuthManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.karakoc.sofra.model.LoginRequest;
import com.karakoc.sofra.model.LoginResponse;

import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
public class AuthController {


    private final AuthManager authManager;


    @PostMapping("/account/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authManager.attemptLogin(request.getEmail(),request.getPassword());
    }
    @PostMapping("/account/register")
    public UserDTO register(@RequestBody RegisterRequest request) {
        return authManager.attemptRegister(request.getEmail(),request.getPassword());
    }
    @GetMapping("/account/getMe")
    public UserDTO getMe(@AuthenticationPrincipal UserPrincipal principal){
        return UserDTO.builder()
            .id(principal.getUserId())
            .email(principal.getEmail())
            .role(principal.getAuthorities().toString())
            .extraInfo(principal.getExtraInfo())
            .build();
    }

}
