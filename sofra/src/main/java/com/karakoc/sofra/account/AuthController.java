package com.karakoc.sofra.account;

import com.karakoc.sofra.account.requests.LoginRequest;
import com.karakoc.sofra.account.requests.LoginResponse;
import com.karakoc.sofra.account.requests.RegisterRequest;
import com.karakoc.sofra.exceptions.general.ForbiddenException;
import com.karakoc.sofra.user.UserDTO;
import com.karakoc.sofra.security.UserPrincipal;
import com.karakoc.sofra.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;
    private final UserService userService;


    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.attemptLogin(request.getEmail(),request.getPassword());
    }
    @PostMapping("/register")
    public UserDTO register(@RequestBody RegisterRequest request) {
        return authService.attemptRegister(request.getEmail(),request.getPassword());
    }
    @GetMapping("/getMe")
    public UserDTO getMe(@AuthenticationPrincipal UserPrincipal principal){
        return userService.getUser(principal.getEmail());
    }
    @PutMapping("/type/seller/{id}")
    public UserDTO switchUserType(@AuthenticationPrincipal UserPrincipal principal){
        return userService.switchUserType(principal.getUserId());
    }

}
