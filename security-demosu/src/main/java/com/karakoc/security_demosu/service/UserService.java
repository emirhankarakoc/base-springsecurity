package com.karakoc.security_demosu.service;

import com.karakoc.security_demosu.entity.User;
import com.karakoc.security_demosu.model.UserDTO;
import com.karakoc.security_demosu.security.UserPrincipal;

public interface UserService {
    UserDTO createUser(String email, String password);
    UserDTO getUser(String email, UserPrincipal principal);
}
