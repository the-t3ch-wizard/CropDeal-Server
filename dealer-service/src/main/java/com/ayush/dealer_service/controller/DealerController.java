package com.ayush.dealer_service.controller;

import java.util.List;

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

import com.ayush.dealer_service.dto.DealerUpdationDto;
import com.ayush.dealer_service.model.Dealer;
import com.ayush.dealer_service.service.DealerService;

@RestController
@RequestMapping("/dealer")
public class DealerController {

	@Autowired
	DealerService dealerService;
	
	@PostMapping
	public ResponseEntity<Dealer> createDealer(@RequestBody Dealer dealer){
		return dealerService.createDealer(dealer);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Dealer>> getAllDealer(){
		return dealerService.getAllDealers();
	}
	
	@GetMapping
	public ResponseEntity<Dealer> getDealerById(@RequestParam Long id){
		return dealerService.getDealerById(id);
	}
	
	@PutMapping
	public ResponseEntity<Dealer> updateDealerById(@RequestParam Long id, @RequestBody DealerUpdationDto dealerUpdationDto){
		return dealerService.updateDealer(id, dealerUpdationDto);
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteDealerById(@RequestParam Long id){
		return dealerService.deleteDealer(id);
	}
	
}
