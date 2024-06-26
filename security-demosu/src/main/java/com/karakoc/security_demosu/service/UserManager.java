package com.karakoc.security_demosu.service;

import com.karakoc.security_demosu.entity.User;
import com.karakoc.security_demosu.exceptions.general.BadRequestException;
import com.karakoc.security_demosu.exceptions.general.ForbiddenException;
import com.karakoc.security_demosu.exceptions.general.NotfoundException;
import com.karakoc.security_demosu.exceptions.strings.ExceptionMessages;
import com.karakoc.security_demosu.model.UserDTO;
import com.karakoc.security_demosu.repository.UserRepository;
import com.karakoc.security_demosu.security.UserPrincipal;
import com.karakoc.security_demosu.security.WebSecurityConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.karakoc.security_demosu.entity.User.userToDTO;

@Service
@AllArgsConstructor
public class UserManager implements UserService{

    private final UserRepository repository;
    private final ExceptionMessages messages;
    private final WebSecurityConfig webSecurityConfig;
    public UserDTO createUser(String email, String password) {
        // Kullanıcının zaten mevcut olup olmadığını kontrol edin
        if (repository.findUserByEmail(email).isPresent()) {
            throw new BadRequestException("Email already exists");
        }

        // Kullanıcı oluşturun ve şifreyi şifreleyin
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail(email);
        user.setPassword(webSecurityConfig.passwordEncoder().encode(password));
        user.setRole("ROLE_USER");
        user.setExtraInfo("My nice USER");

        // Kullanıcıyı veritabanına kaydedin
        return userToDTO(repository.save(user));
    }
    public UserDTO getUser(String email, UserPrincipal principal) {
        //if (principal==null) throw new ForbiddenException(messages.getLOG_IN_FIRST());
        User user = repository.findUserByEmail(email).orElseThrow(()-> new NotfoundException(messages.getUSER_NOT_FOUND_404()));
        var dto = userToDTO(user);
        dto.setExtraInfo("Requested for:" + principal.getUsername());
        return dto;
    }

}
