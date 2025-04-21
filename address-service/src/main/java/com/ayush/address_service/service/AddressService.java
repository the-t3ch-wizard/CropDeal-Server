package com.ayush.address_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ayush.address_service.dto.AddressUpdationDto;
import com.ayush.address_service.exception.ResourceNotFoundException;
import com.ayush.address_service.model.Address;
import com.ayush.address_service.repository.AddressRepository;

import java.util.List;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;

	public ResponseEntity<Address> createAddress(Address address) {
		Address savedAddress = addressRepository.save(address);
		return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
	}

	public ResponseEntity<Address> getAddressById(Long id) {
		Address address = addressRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + id));
		return new ResponseEntity<>(address, HttpStatus.OK);
	}

	public ResponseEntity<Address> updateAddress(Long id, AddressUpdationDto addressDto) {
		Address existingAddress = addressRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + id));
			
		if (addressDto.getAddressLine1() != null) existingAddress.setAddressLine1(addressDto.getAddressLine1());
		if (addressDto.getAddressLine2() != null) existingAddress.setAddressLine2(addressDto.getAddressLine2());
		if (addressDto.getCity() != null) existingAddress.setCity(addressDto.getCity());
		if (addressDto.getState() != null) existingAddress.setState(addressDto.getState());
		if (addressDto.getPostalCode() != null) existingAddress.setPostalCode(addressDto.getPostalCode());

		Address updatedAddress = addressRepository.save(existingAddress);
		return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
	}

	public ResponseEntity<String> deleteAddress(Long id) {
		Address address = addressRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + id));
			
		addressRepository.delete(address);
		return new ResponseEntity<>("Address deleted successfully", HttpStatus.OK);
	}

	public ResponseEntity<String> deleteAddressesByCropId(Long cropId) {
		List<Address> addresses = addressRepository.findByCropId(cropId);
		addressRepository.deleteAll(addresses);
		return new ResponseEntity<>("All addresses for farmer deleted successfully", HttpStatus.OK);
	}
}
