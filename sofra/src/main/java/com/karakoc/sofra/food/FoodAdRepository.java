package com.karakoc.sofra.food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodAdRepository extends JpaRepository<FoodAd,String> {


    List<FoodAd> findAllByAdStatus(AdStatus adStatus);
    List<FoodAd> findAllBySellerId(String sellerId);
}
