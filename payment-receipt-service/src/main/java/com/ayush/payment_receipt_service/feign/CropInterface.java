package com.ayush.payment_receipt_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ayush.payment_receipt_service.model.Crop;

@FeignClient(name = "CROP-SERVICE")
public interface CropInterface {
    @GetMapping("/crop")
    ResponseEntity<Crop> getCropById(@RequestParam Long id);
}