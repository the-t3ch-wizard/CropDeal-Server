package com.ayush.crop_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayush.crop_service.exception.ResourceNotFoundException;
import com.ayush.crop_service.exception.InvalidRequestException;
import com.ayush.crop_service.feign.FarmerInterface;
import com.ayush.crop_service.model.Crop;
import com.ayush.crop_service.model.Farmer;
import com.ayush.crop_service.repository.CropRepository;

import com.ayush.crop_service.dto.CropUpdationDto;

import java.util.List;

@Service
public class CropService {

	@Autowired
	private CropRepository cropRepository;
	
	@Autowired
	private FarmerInterface farmerInterface;

	@Transactional
	public ResponseEntity<Crop> createCrop(Crop crop) {
		validateCrop(crop);
		
		// Verify farmer exists and is active
		ResponseEntity<Farmer> farmerResponse = farmerInterface.getFarmerById(crop.getFarmerId());
		Farmer farmer = farmerResponse.getBody();
		
		if (farmer == null || !farmer.getStatus().toString().equals("ACTIVE")) {
			throw new InvalidRequestException("Farmer is not active or does not exist");
		}
		
		crop.setFarmerId(crop.getFarmerId());
		
		Crop savedCrop = cropRepository.save(crop);
		return new ResponseEntity<>(savedCrop, HttpStatus.CREATED);
	}

	public ResponseEntity<Crop> getCropById(Long id) {
		Crop crop = cropRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Crop not found with ID: " + id));
		return new ResponseEntity<>(crop, HttpStatus.OK);
	}

	public ResponseEntity<List<Crop>> getAllCrops() {
		List<Crop> crops = cropRepository.findAll();
		return new ResponseEntity<>(crops, HttpStatus.OK);
	}

	public ResponseEntity<List<Crop>> getCropsByFarmerId(Long farmerId) {
		// Verify farmer exists
		farmerInterface.getFarmerById(farmerId);
		
		List<Crop> crops = cropRepository.findByFarmerId(farmerId);
		return new ResponseEntity<>(crops, HttpStatus.OK);
	}

	public ResponseEntity<List<Crop>> getCropsByType(String cropType) {
		List<Crop> crops = cropRepository.findByCropType(cropType);
		return new ResponseEntity<>(crops, HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<Crop> updateCropById(Long id, CropUpdationDto cropDto) {
		Crop existingCrop = cropRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Crop not found with ID: " + id));
			
		if (cropDto.getName() != null) {
			if (cropDto.getName().trim().isEmpty()) {
				throw new InvalidRequestException("Crop name cannot be empty");
			}
			existingCrop.setName(cropDto.getName());
		}
		
		if (cropDto.getCropType() != null) {
			if (cropDto.getCropType().trim().isEmpty()) {
				throw new InvalidRequestException("Crop type cannot be empty");
			}
			existingCrop.setCropType(cropDto.getCropType());
		}
		
		if (cropDto.getQuantityInKg() != null) {
			if (cropDto.getQuantityInKg() <= 0) {
				throw new InvalidRequestException("Quantity must be positive");
			}
			existingCrop.setQuantityInKg(cropDto.getQuantityInKg());
		}
		
		if (cropDto.getPricePerKg() != null) {
			if (cropDto.getPricePerKg() <= 0) {
				throw new InvalidRequestException("Price must be positive");
			}
			existingCrop.setPricePerKg(cropDto.getPricePerKg());
		}

		Crop updatedCrop = cropRepository.save(existingCrop);
		return new ResponseEntity<>(updatedCrop, HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<String> deleteCropById(Long id) {
		Crop crop = cropRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Crop not found with ID: " + id));
			
		cropRepository.delete(crop);
		return new ResponseEntity<>("Crop deleted successfully", HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<String> deleteCropsByFarmerId(Long farmerId) {
		// Verify farmer exists
		farmerInterface.getFarmerById(farmerId);
		
		cropRepository.deleteByFarmerId(farmerId);
		return new ResponseEntity<>("All crops for farmer deleted successfully", HttpStatus.OK);
	}

	private void validateCrop(Crop crop) {
		if (crop.getName() == null || crop.getName().trim().isEmpty()) {
			throw new InvalidRequestException("Crop name is mandatory");
		}
		
		if (crop.getCropType() == null || crop.getCropType().trim().isEmpty()) {
			throw new InvalidRequestException("Crop type is mandatory");
		}
		
		if (crop.getQuantityInKg() == null || crop.getQuantityInKg() <= 0) {
			throw new InvalidRequestException("Quantity must be positive");
		}
		
		if (crop.getPricePerKg() == null || crop.getPricePerKg() <= 0) {
			throw new InvalidRequestException("Price must be positive");
		}
		
		if (crop.getFarmerId() == null) {
			throw new InvalidRequestException("Farmer information is mandatory");
		}
	}
}
