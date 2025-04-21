package com.ayush.dealer_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ayush.dealer_service.exception.ResourceNotFoundException;
import com.ayush.dealer_service.feign.AddressInterface;
import com.ayush.dealer_service.model.Dealer;
import com.ayush.dealer_service.repository.DealerRepository;
import com.ayush.dealer_service.dto.DealerUpdationDto;

import java.util.List;

@Service
public class DealerService {

	@Autowired
	private DealerRepository dealerRepository;
	
	@Autowired
	private AddressInterface addressInterface;

	public ResponseEntity<Dealer> createDealer(Dealer dealer) {
		Dealer savedDealer = dealerRepository.save(dealer);
		return new ResponseEntity<>(savedDealer, HttpStatus.CREATED);
	}

	public ResponseEntity<Dealer> getDealerById(Long id) {
		Dealer dealer = dealerRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Dealer not found with ID: " + id));
		return new ResponseEntity<>(dealer, HttpStatus.OK);
	}

	public ResponseEntity<List<Dealer>> getAllDealers() {
		List<Dealer> dealers = dealerRepository.findAll();
		return new ResponseEntity<>(dealers, HttpStatus.OK);
	}

	public ResponseEntity<List<Dealer>> getDealersByStatus(String status) {
		List<Dealer> dealers = dealerRepository.findByStatus(status);
		return new ResponseEntity<>(dealers, HttpStatus.OK);
	}

	public ResponseEntity<Dealer> updateDealer(Long id, DealerUpdationDto dealerDto) {
		Dealer existingDealer = dealerRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Dealer not found with ID: " + id));
			
		if (dealerDto.getStatus() != null) {
			existingDealer.setStatus(dealerDto.getStatus());
		}

		Dealer updatedDealer = dealerRepository.save(existingDealer);
		return new ResponseEntity<>(updatedDealer, HttpStatus.OK);
	}

	public ResponseEntity<String> deleteDealer(Long id) {
		Dealer dealer = dealerRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Dealer not found with ID: " + id));
		
		dealerRepository.delete(dealer);
		return new ResponseEntity<>("Dealer deleted successfully", HttpStatus.OK);
	}
}
