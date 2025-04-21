package com.ayush.payment_receipt_service.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Crop {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Crop name is mandatory")
    private String name;

    @NotBlank(message = "Crop type is mandatory")
    private String cropType;

    @NotNull(message = "Quantity is mandatory")
    @Positive(message = "Quantity must be positive")
    private Double quantityInKg;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be positive")
    private Double pricePerKg;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmer_id", nullable = false)
    private Farmer farmer;

    @OneToOne(mappedBy = "crop")
    private PaymentReceipt PaymentReceipt;
	
}
