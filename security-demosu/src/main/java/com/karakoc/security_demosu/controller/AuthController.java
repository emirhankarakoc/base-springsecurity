package com.karakoc.security_demosu.controller;

import com.karakoc.security_demosu.model.RegisterRequest;
import com.karakoc.security_demosu.model.UserDTO;
import com.karakoc.security_demosu.security.UserPrincipal;
import com.karakoc.security_demosu.service.AuthManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.karakoc.security_demosu.model.LoginRequest;
import com.karakoc.security_demosu.model.LoginResponse;

import lombok.AllArgsConstructor;

import static com.karakoc.security_demosu.entity.User.userToDTO;


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
