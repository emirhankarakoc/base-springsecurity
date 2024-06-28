package com.karakoc.sofra.food;


import jakarta.persistence.Column;
import lombok.Data;

@Data
public class FoodAdShortDTO {
    private String name;
    private String description;
    private double price;
}
