package com.ayush.farmer_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ayush.farmer_service.dto.FarmerDto;
import com.ayush.farmer_service.exception.ResourceNotFoundException;
import com.ayush.farmer_service.feign.BankAccountInterface;
import com.ayush.farmer_service.model.Farmer;
import com.ayush.farmer_service.repository.FarmerRepository;

@Service
public class FarmerService {

	@Autowired
	private FarmerRepository farmerRepository;
	
	@Autowired
	private BankAccountInterface bankAccountInterface;

	public ResponseEntity<Farmer> createFarmer(Farmer farmer) {
		Farmer savedFarmer = farmerRepository.save(farmer);
		return new ResponseEntity<>(savedFarmer, HttpStatus.CREATED);
	}

	public ResponseEntity<Farmer> getFarmerById(Long id) {
		Farmer farmer = farmerRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Farmer not found with ID: " + id));
		return new ResponseEntity<>(farmer, HttpStatus.OK);
	}

	public ResponseEntity<List<Farmer>> getAllFarmers() {
		List<Farmer> farmers = farmerRepository.findAll();
		return new ResponseEntity<>(farmers, HttpStatus.OK);
	}

	public ResponseEntity<Farmer> updateFarmerById(Long id, FarmerDto farmerDto) {
		Farmer existingFarmer = farmerRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Farmer not found with ID: " + id));
		
		if (farmerDto.getStatus() != null) existingFarmer.setStatus(farmerDto.getStatus());
		if (farmerDto.getRating() != null) existingFarmer.setRating(farmerDto.getRating());

		Farmer updatedFarmer = farmerRepository.save(existingFarmer);
		return new ResponseEntity<>(updatedFarmer, HttpStatus.OK);
	}

	public ResponseEntity<String> deleteFarmerById(Long id) {
		Farmer farmer = farmerRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Farmer not found with ID: " + id));
		
		// Delete associated bank account
		bankAccountInterface.deleteBankAccountByFarmerId(id);
		
		farmerRepository.delete(farmer);
		return new ResponseEntity<>("Farmer deleted successfully", HttpStatus.OK);
	}
}
