package com.ayush.address_service.dto;

import lombok.Data;

@Data
public class AddressResponseDto {
    private Long id;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
}