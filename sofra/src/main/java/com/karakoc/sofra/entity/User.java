package com.karakoc.sofra.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.karakoc.sofra.model.UserDTO;
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

    public static UserDTO userToDTO(User user){
        var dto = UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .extraInfo(user.getExtraInfo())
                .build();
        return dto;
    }
}
