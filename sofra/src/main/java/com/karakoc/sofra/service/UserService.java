package com.karakoc.sofra.service;

import com.karakoc.sofra.model.UserDTO;
import com.karakoc.sofra.security.UserPrincipal;

public interface UserService {
    UserDTO createUser(String email, String password);
    UserDTO getUser(String email, UserPrincipal principal);
}
