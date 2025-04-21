package com.ayush.crop_service.controller;

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

import com.ayush.crop_service.dto.CropUpdationDto;
import com.ayush.crop_service.model.Crop;
import com.ayush.crop_service.service.CropService;

@RestController
@RequestMapping("/crop")
public class CropController {

	@Autowired
	CropService cropService;
	
	@PostMapping
	public ResponseEntity<Crop> createCrop(@RequestBody Crop crop){
		return cropService.createCrop(crop);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Crop>> getAllCrop(){
		return cropService.getAllCrops();
	}
	
	@GetMapping
	public ResponseEntity<Crop> getCropById(@RequestParam Long id){
		return cropService.getCropById(id);
	}
	
	@PutMapping
	public ResponseEntity<Crop> updateCropById(@RequestParam Long id, @RequestBody CropUpdationDto cropUpdationDto){
		return cropService.updateCropById(id, cropUpdationDto);
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteCropById(@RequestParam Long id){
		return cropService.deleteCropById(id);
	}
	
}
