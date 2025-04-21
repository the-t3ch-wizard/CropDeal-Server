package com.ayush.payment_receipt_service.dto;

import java.time.LocalDateTime;

import com.ayush.payment_receipt_service.model.PaymentStatus;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PaymentReceiptUpdationDto {

	@Positive(message = "Quantity must be positive")
    private Double quantityInKg;

    @Positive(message = "Price must be positive")
    private Double pricePerKg;

    private Double finalAmount;

    private LocalDateTime billDate;

    private PaymentStatus paymentStatus;
	
}
