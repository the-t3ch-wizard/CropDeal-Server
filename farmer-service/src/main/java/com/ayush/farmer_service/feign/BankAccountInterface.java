package com.ayush.farmer_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ayush.farmer_service.model.BankAccount;

@FeignClient(name = "BANK-ACCOUNT-SERVICE")
public interface BankAccountInterface {
    @GetMapping("/bank-account")
    ResponseEntity<BankAccount> getBankAccountByFarmerId(@RequestParam Long farmerId);
    
    @DeleteMapping("/bank-account")
    ResponseEntity<String> deleteBankAccountByFarmerId(@RequestParam Long farmerId);
}