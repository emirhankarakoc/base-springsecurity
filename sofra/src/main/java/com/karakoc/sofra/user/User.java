package com.karakoc.sofra.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {
    //buraya birsey eklersen, gidip UserPrincipal'a da ekle. orasi senin db scheman gibi birseyin.
    @Id
    private String id;
    private String email;
    @JsonIgnore
    private String password;
    private String role;
    private String extraInfo;
    private double balance;

    public static UserDTO userToDTO(User user){
        var dto = UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .extraInfo(user.getExtraInfo())
                .balance(user.getBalance())
                .build();
        return dto;
    }
}
