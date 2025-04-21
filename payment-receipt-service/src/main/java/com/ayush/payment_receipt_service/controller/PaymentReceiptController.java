package com.ayush.payment_receipt_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayush.payment_receipt_service.dto.PaymentReceiptUpdationDto;
import com.ayush.payment_receipt_service.model.PaymentReceipt;
import com.ayush.payment_receipt_service.service.PaymentReceiptService;

@RestController
@RequestMapping("/payment-receipt")
public class PaymentReceiptController {

	@Autowired
	PaymentReceiptService paymentReceiptService;
	
	@PostMapping
	public ResponseEntity<PaymentReceipt> createPaymentReceipt(@RequestBody PaymentReceipt dealer){
		return paymentReceiptService.createPaymentReceipt(dealer);
	}
	
	@GetMapping
	public ResponseEntity<PaymentReceipt> getPaymentReceiptById(@RequestParam Long id){
		return paymentReceiptService.getPaymentReceiptById(id);
	}
	
	@PutMapping
	public ResponseEntity<PaymentReceipt> updatePaymentReceiptById(@RequestParam Long id, @RequestBody PaymentReceiptUpdationDto paymentReceiptUpdationDto){
		return paymentReceiptService.updatePaymentReceipt(id, paymentReceiptUpdationDto);
	}
	
	@DeleteMapping
	public ResponseEntity<String> deletePaymentReceiptById(@RequestParam Long id){
		return paymentReceiptService.deletePaymentReceipt(id);
	}
	
}
