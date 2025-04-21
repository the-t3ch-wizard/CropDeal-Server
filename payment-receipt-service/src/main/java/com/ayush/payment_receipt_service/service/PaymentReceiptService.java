package com.ayush.payment_receipt_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayush.payment_receipt_service.dto.PaymentReceiptUpdationDto;
import com.ayush.payment_receipt_service.exception.ResourceNotFoundException;
import com.ayush.payment_receipt_service.feign.CropInterface;
import com.ayush.payment_receipt_service.feign.DealerInterface;
import com.ayush.payment_receipt_service.model.PaymentReceipt;
import com.ayush.payment_receipt_service.repository.PaymentReceiptRepository;

import java.util.List;

@Service
public class PaymentReceiptService {

	@Autowired
	private PaymentReceiptRepository receiptRepository;
	
	@Autowired
	private CropInterface cropInterface;
	
	@Autowired
	private DealerInterface dealerInterface;

	@Transactional
	public ResponseEntity<PaymentReceipt> createPaymentReceipt(PaymentReceipt receipt) {
		validatePaymentReceipt(receipt);
		
		try {
			// Verify crop and dealer exist
			cropInterface.getCropById(receipt.getCrop().getId());
			dealerInterface.getDealerById(receipt.getDealer().getId());
			
			PaymentReceipt savedReceipt = receiptRepository.save(receipt);
			return new ResponseEntity<>(savedReceipt, HttpStatus.CREATED);
		} catch (Exception ex) {
			throw new ResourceNotFoundException("Failed to create payment receipt: " + ex.getMessage());
		}
	}

	public ResponseEntity<PaymentReceipt> getPaymentReceiptById(Long id) {
		PaymentReceipt receipt = receiptRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Payment receipt not found with ID: " + id));
		return new ResponseEntity<>(receipt, HttpStatus.OK);
	}

	public ResponseEntity<List<PaymentReceipt>> getPaymentReceiptsByDealerId(Long dealerId) {
		try {
			// Verify dealer exists
			dealerInterface.getDealerById(dealerId);
			List<PaymentReceipt> receipts = receiptRepository.findByDealerId(dealerId);
			return new ResponseEntity<>(receipts, HttpStatus.OK);
		} catch (Exception ex) {
			throw new ResourceNotFoundException("Failed to fetch receipts for dealer: " + ex.getMessage());
		}
	}

	public ResponseEntity<PaymentReceipt> getPaymentReceiptByCropId(Long cropId) {
		PaymentReceipt receipt = receiptRepository.findByCropId(cropId)
			.orElseThrow(() -> new ResourceNotFoundException("Payment receipt not found for crop ID: " + cropId));
		return new ResponseEntity<>(receipt, HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<PaymentReceipt> updatePaymentReceipt(Long id, PaymentReceiptUpdationDto receiptDto) {
		PaymentReceipt existingReceipt = receiptRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Payment receipt not found with ID: " + id));
			
		validateUpdateDto(receiptDto);
		updateReceiptFromDto(existingReceipt, receiptDto);

		PaymentReceipt updatedReceipt = receiptRepository.save(existingReceipt);
		return new ResponseEntity<>(updatedReceipt, HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<String> deletePaymentReceipt(Long id) {
		PaymentReceipt receipt = receiptRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Payment receipt not found with ID: " + id));
			
		receiptRepository.delete(receipt);
		return new ResponseEntity<>("Payment receipt deleted successfully", HttpStatus.OK);
	}

	private void validatePaymentReceipt(PaymentReceipt receipt) {
		if (receipt == null) {
			throw new IllegalArgumentException("Payment receipt cannot be null");
		}
		if (receipt.getCrop() == null || receipt.getCrop().getId() == null) {
			throw new IllegalArgumentException("Crop information is required");
		}
		if (receipt.getDealer() == null || receipt.getDealer().getId() == null) {
			throw new IllegalArgumentException("Dealer information is required");
		}
		if (receipt.getQuantityInKg() == null || receipt.getPricePerKg() == null || 
			receipt.getFinalAmount() == null || receipt.getFinalAmount() <= 0) {
			throw new IllegalArgumentException("Valid amount information is required");
		}
		if (receipt.getPaymentStatus() == null) {
			throw new IllegalArgumentException("Payment status is required");
		}
	}

	private void validateUpdateDto(PaymentReceiptUpdationDto dto) {
		if (dto.getFinalAmount() != null && dto.getFinalAmount() <= 0) {
			throw new ResourceNotFoundException("Amount must be positive");
		}
		if (dto.getPaymentStatus() != null && dto.getPaymentStatus().toString().trim().isEmpty()) {
			throw new ResourceNotFoundException("Payment status cannot be empty");
		}
	}

	private void updateReceiptFromDto(PaymentReceipt receipt, PaymentReceiptUpdationDto dto) {
		if (dto.getFinalAmount() != null) receipt.setFinalAmount(dto.getFinalAmount());
		if (dto.getPaymentStatus() != null) receipt.setPaymentStatus(dto.getPaymentStatus());
	}
}
