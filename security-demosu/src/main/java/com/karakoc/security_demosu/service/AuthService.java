package com.karakoc.security_demosu.service;

import com.karakoc.security_demosu.entity.User;
import com.karakoc.security_demosu.model.LoginResponse;
import com.karakoc.security_demosu.model.UserDTO;

public interface AuthService {
    LoginResponse attemptLogin(String email, String password);
    UserDTO attemptRegister(String email, String password);
}
