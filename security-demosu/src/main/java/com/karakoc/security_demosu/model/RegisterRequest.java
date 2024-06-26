package com.karakoc.security_demosu.model;


import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
}
