package com.karakoc.sofra.service;

import com.karakoc.sofra.entity.User;
import com.karakoc.sofra.exceptions.general.BadRequestException;
import com.karakoc.sofra.exceptions.general.NotfoundException;
import com.karakoc.sofra.exceptions.strings.ExceptionMessages;
import com.karakoc.sofra.model.UserDTO;
import com.karakoc.sofra.repository.UserRepository;
import com.karakoc.sofra.security.UserPrincipal;
import com.karakoc.sofra.security.WebSecurityConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.karakoc.sofra.entity.User.userToDTO;

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
