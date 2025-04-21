package com.ayush.address_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayush.address_service.dto.AddressUpdationDto;
import com.ayush.address_service.model.Address;
import com.ayush.address_service.service.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	@PostMapping
	public ResponseEntity<Address> createAddress(@RequestBody Address address) {
		return addressService.createAddress(address);
	}
	
	@GetMapping
	public ResponseEntity<Address> getAddressById(@RequestParam Long id) {
		return addressService.getAddressById(id);
	}
	
	@PutMapping
	public ResponseEntity<Address> updateAddress(@RequestParam Long id, @RequestBody AddressUpdationDto addressDto) {
		return addressService.updateAddress(id, addressDto);
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteAddress(@RequestParam Long id) {
		return addressService.deleteAddress(id);
	}
	
	@DeleteMapping("/farmer/{farmerId}")
	public ResponseEntity<String> deleteAddressesByCropId(@PathVariable Long cropId) {
		return addressService.deleteAddressesByCropId(cropId);
	}
}
