package com.karakoc.sofra.service;

import com.karakoc.sofra.model.LoginResponse;
import com.karakoc.sofra.model.UserDTO;
import com.karakoc.sofra.security.TokenManager;
import com.karakoc.sofra.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthManager implements AuthService{
    private final TokenManager tokenManager;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public LoginResponse attemptLogin(String email, String password) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var principal = (UserPrincipal) authentication.getPrincipal();
        var roles = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        var token = tokenManager.issue(principal.getUserId(), principal.getEmail(), roles);
        return LoginResponse.builder()
                .accessToken(token)
                .build();
    }


    public UserDTO attemptRegister(String email, String password){
        return userService.createUser(email,password);
    }
}
