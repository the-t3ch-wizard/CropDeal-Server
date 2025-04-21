package com.ayush.payment_receipt_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PaymentReceiptServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentReceiptServiceApplication.class, args);
	}

}
