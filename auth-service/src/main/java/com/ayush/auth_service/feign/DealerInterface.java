package com.ayush.auth_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ayush.auth_service.model.Dealer;

@FeignClient(name = "DEALER-SERVICE")
public interface DealerInterface {

	@PostMapping("/dealer")
	public ResponseEntity<Dealer> createDealer(@RequestBody Dealer dealer);
	
}
