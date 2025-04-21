package com.ayush.payment_receipt_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ayush.payment_receipt_service.model.Dealer;

@FeignClient(name = "DEALER-SERVICE")
public interface DealerInterface {
    @GetMapping("/dealer")
    ResponseEntity<Dealer> getDealerById(@RequestParam Long id);
}