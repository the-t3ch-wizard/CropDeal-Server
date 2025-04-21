package com.ayush.bank_account_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BankAccountCreationDto {
    
    @NotNull(message = "Farmer ID is mandatory")
    private Long farmerId;

    @NotBlank(message = "Account number is mandatory")
    @Size(min = 9, max = 18, message = "Account number must be between 9 and 18 characters")
    private String accountNumber;

    @NotBlank(message = "IFSC code is mandatory")
    private String ifscCode;

    @NotBlank(message = "Account holder name is mandatory")
    @Size(min = 2, max = 100, message = "Account holder name must be between 2 and 100 characters")
    private String accountHolderName;
}