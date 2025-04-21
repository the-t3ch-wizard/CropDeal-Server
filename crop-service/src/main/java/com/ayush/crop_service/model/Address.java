package com.ayush.crop_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Address line 1 is mandatory")
    @Size(max = 100, message = "Address line 1 cannot exceed 100 characters")
    private String addressLine1;

    @Size(max = 100, message = "Address line 2 cannot exceed 100 characters")
    private String addressLine2;

    @NotBlank(message = "City is mandatory")
    @Size(max = 50, message = "City cannot exceed 50 characters")
    private String city;

    @NotBlank(message = "State is mandatory")
    @Size(max = 50, message = "State cannot exceed 50 characters")
    private String state;

    @NotBlank(message = "Postal code is mandatory")
    @Pattern(regexp = "^[0-9]{6}$", message = "Postal code must be 6 digits")
    private String postalCode;

    @OneToOne(mappedBy = "address")
    private Crop crop;
	
}
