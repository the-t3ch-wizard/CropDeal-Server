package com.ayush.review_service.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ReviewResponseDto {
    private Long id;
    private Long farmerId;
    private Long dealerId;
    private Double rating;
    private String feedback;
    private LocalDateTime createdAt;
}