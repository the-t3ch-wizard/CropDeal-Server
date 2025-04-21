package com.ayush.review_service.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReviewCreationDto {
    
    @NotNull(message = "Farmer ID is mandatory")
    private Long farmerId;

    @NotNull(message = "Dealer ID is mandatory")
    private Long dealerId;

    @DecimalMin(value = "0.0", message = "Rating must be at least 0.0")
    @DecimalMax(value = "5.0", message = "Rating must be at most 5.0")
    private Double rating;

    @NotBlank(message = "Feedback is mandatory")
    @Size(max = 1000, message = "Feedback cannot exceed 1000 characters")
    private String feedback;
}