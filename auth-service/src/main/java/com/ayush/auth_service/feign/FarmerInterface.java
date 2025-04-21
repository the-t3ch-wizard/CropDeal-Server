package com.ayush.auth_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ayush.auth_service.model.Farmer;

@FeignClient(name = "FARMER-SERVICE")
public interface FarmerInterface {

	@PostMapping("/farmer")
	public ResponseEntity<Farmer> createFarmer(@RequestBody Farmer farmer);
	
}
