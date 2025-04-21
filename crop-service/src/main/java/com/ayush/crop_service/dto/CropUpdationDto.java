package com.ayush.crop_service.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CropUpdationDto {

    private String name;

    private String cropType;

    @Positive(message = "Quantity must be positive")
    private Double quantityInKg;

    @Positive(message = "Price must be positive")
    private Double pricePerKg;
	
}
