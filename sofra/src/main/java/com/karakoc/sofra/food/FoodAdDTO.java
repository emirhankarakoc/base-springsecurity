package com.karakoc.sofra.food;

import lombok.Data;

import java.time.LocalDateTime;

@Data
//data transfer object
public class FoodAdDTO {
    private String id;
    private String sellerId;
    private String name;
    private String description;
    private double price;
    private AdStatus adStatus;
    private LocalDateTime sharedAt;
    private String photoPath;
    private double latitude;
    private double longitude;
}
