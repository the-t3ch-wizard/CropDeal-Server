package com.ayush.dealer_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ayush.dealer_service.model.Address;

@FeignClient(name = "ADDRESS-SERVICE")
public interface AddressInterface {
    @GetMapping("/address")
    ResponseEntity<Address> getAddressById(@RequestParam Long id);
    
    @DeleteMapping("/address/dealer/{dealerId}")
    ResponseEntity<String> deleteAddressByDealerId(@PathVariable Long dealerId);
}