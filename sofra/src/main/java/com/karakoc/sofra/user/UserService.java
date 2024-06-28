package com.karakoc.sofra.user;

import com.karakoc.sofra.food.FoodAdDTO;
import com.karakoc.sofra.food.FoodAdShortDTO;
import com.karakoc.sofra.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;

public interface UserService {
    UserDTO createUser(String email, String password);
    UserDTO getUser(String email);
    UserDTO switchUserType(String userId);
    String deleteUser(String email);
    UserDTO increaseAccountBalance(double amount,String userId);
    List<FoodAdShortDTO> listAllFoodAdShorts();
    //admin
    List<FoodAdDTO> listAllFoodAds();
    FoodAdDTO getFoodAdDTO(String id);
    ResponseEntity buyFoodAd(String adId, String userId) throws IOException;
}
