package com.ayush.bank_account_service.controller;

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

import com.ayush.bank_account_service.dto.BankAccountUpdationDto;
import com.ayush.bank_account_service.model.BankAccount;
import com.ayush.bank_account_service.service.BankAccountService;

@RestController
@RequestMapping("/bank-account")
public class BankAccountController {

	@Autowired
	BankAccountService bankAccountService;
	
	@PostMapping
	public ResponseEntity<BankAccount> createBankAccount(@RequestBody BankAccount bankAccount){
		return bankAccountService.createBankAccount(bankAccount);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<BankAccount>> getAllBankAccount(){
		return bankAccountService.getAllBankAccounts();
	}
	
	@GetMapping
	public ResponseEntity<BankAccount> getBankAccountById(@RequestParam Long id){
		return bankAccountService.getBankAccountById(id);
	}
	
	@PutMapping
	public ResponseEntity<BankAccount> updateBankAccountById(@RequestParam Long id, @RequestBody BankAccountUpdationDto bankAccountUpdationDto){
		return bankAccountService.updateBankAccount(id, bankAccountUpdationDto);
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteBankAccountById(@RequestParam Long id){
		return bankAccountService.deleteBankAccount(id);
	}
	
}
