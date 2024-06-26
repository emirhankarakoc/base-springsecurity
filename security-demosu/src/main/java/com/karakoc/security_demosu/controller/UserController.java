package com.karakoc.security_demosu.controller;

import com.karakoc.security_demosu.model.UserDTO;
import com.karakoc.security_demosu.security.UserPrincipal;
import com.karakoc.security_demosu.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping
    public UserDTO getUser(@RequestParam String email, @AuthenticationPrincipal UserPrincipal principal){
        return userService.getUser(email,principal);
    }
}
