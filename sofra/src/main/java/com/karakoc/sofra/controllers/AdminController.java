package com.karakoc.sofra.controllers;


import com.karakoc.sofra.food.FoodAdDTO;
import com.karakoc.sofra.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    //kisi silebilmeli.
    //ilan silebilmeli
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{email}")
    public String deleteUser( @PathVariable String email){

        return userService.deleteUser(email);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<FoodAdDTO> getAllFoodAds(){
        return userService.listAllFoodAds();
    }
}
