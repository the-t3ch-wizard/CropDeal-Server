package com.ayush.farmer_service.controller;

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

import com.ayush.farmer_service.dto.FarmerDto;
import com.ayush.farmer_service.model.Farmer;
import com.ayush.farmer_service.service.FarmerService;

@RestController
@RequestMapping("/farmer")
public class FarmerController {
	
	@Autowired
	FarmerService farmerService;
	
	@PostMapping
	public ResponseEntity<Farmer> createFarmer(@RequestBody Farmer farmer) {
		return farmerService.createFarmer(farmer);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Farmer>> getAllFarmers() {
		return farmerService.getAllFarmers();
	}
	
	@GetMapping
	public ResponseEntity<Farmer> getFarmerById(@RequestParam Long id) {
		return farmerService.getFarmerById(id);
	}
	
	@PutMapping
	public ResponseEntity<Farmer> updateFarmerById(@RequestParam Long id, @RequestBody FarmerDto farmerDto) {
		return farmerService.updateFarmerById(id, farmerDto);
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteFarmerById(@RequestParam Long id){
		return farmerService.deleteFarmerById(id);
	}
	
}
