package com.ayush.auth_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmer_id", nullable = false)
    private Farmer farmer;

    @NotBlank(message = "Account number is mandatory")
    @Size(min = 9, max = 18, message = "Account number must be between 9 and 18 characters")
    private String accountNumber;

    @NotBlank(message = "IFSC code is mandatory")
    private String ifscCode;

    @NotBlank(message = "Account holder name is mandatory")
    @Size(min = 2, max = 100, message = "Account holder name must be between 2 and 100 characters")
    private String accountHolderName;
	
}
