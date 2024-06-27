package com.karakoc.sofra.service;

import com.karakoc.sofra.model.LoginResponse;
import com.karakoc.sofra.model.UserDTO;

public interface AuthService {
    LoginResponse attemptLogin(String email, String password);
    UserDTO attemptRegister(String email, String password);
}
