package com.ayush.bank_account_service.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BankAccountUpdationDto {
    
    @Size(min = 9, max = 18, message = "Account number must be between 9 and 18 characters")
    private String accountNumber;

    private String ifscCode;

    @Size(min = 2, max = 100, message = "Account holder name must be between 2 and 100 characters")
    private String accountHolderName;
}
