package com.karakoc.sofra.controllers;


import com.karakoc.sofra.food.*;
import com.karakoc.sofra.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("/seller")
@RestController
@RequiredArgsConstructor
public class SellerController {
    private final FoodAdService foodAdService;

    @PostMapping(value = "/ad",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FoodAdDTO postFoodAd(@ModelAttribute CreateFoodAdRequest request, @AuthenticationPrincipal UserPrincipal principal){
        return foodAdService.createFoodAd(request,principal.getUserId());
    }
    //yemek ilanimi satistan kaldirmak istiyorum.
    @DeleteMapping("/ad/{id}")
    public void deleteFoodAd(@PathVariable String id) throws IOException {
        foodAdService.deleteFoodAd(id);
    }

    //yemek ilanlarimi gormek istiyorum HEPSINI
    @GetMapping("/ads")
    public List<FoodAdShortDTO> getAllMyAds(@AuthenticationPrincipal UserPrincipal principal){
        return foodAdService.getAllMyAds(principal.getUserId());
    }
    /*
*
*
*
*
* 1 tane yemek ilanini gormek isteyemem, cunku herkes gorecek. o metod burada degil.
*
*
*
*
     */

    //yemek ilanimi guncellemek istiyorum.







}
