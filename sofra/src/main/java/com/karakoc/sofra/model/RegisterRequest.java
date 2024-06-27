package com.karakoc.sofra.model;


import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
}
