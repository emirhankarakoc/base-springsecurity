package com.karakoc.sofra.food;


import com.karakoc.sofra.security.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;

public interface FoodAdService {
    FoodAdDTO createFoodAd(CreateFoodAdRequest request,String sellerId);
    void deleteFoodAd(String id) throws IOException;
    List<FoodAdShortDTO> getAllMyAds(String sellerId);
}
