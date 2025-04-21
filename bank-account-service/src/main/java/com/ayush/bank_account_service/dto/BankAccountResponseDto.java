package com.ayush.bank_account_service.dto;

import lombok.Data;

@Data
public class BankAccountResponseDto {
    private Long id;
    private Long farmerId;
    private String accountNumber;
    private String ifscCode;
    private String accountHolderName;
}