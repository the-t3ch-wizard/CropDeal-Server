package com.ayush.review_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ayush.review_service.model.Farmer;

@FeignClient(name = "FARMER-SERVICE")
public interface FarmerInterface {
    @GetMapping("/farmer")
    ResponseEntity<Farmer> getFarmerById(@RequestParam Long id);
}