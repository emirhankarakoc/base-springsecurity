package com.karakoc.sofra.food;


import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateFoodAdRequest {
    private MultipartFile multipartFile;
    private String name;
    private String description;
    private double price;
    private double latitude;
    private double longitude;
}
