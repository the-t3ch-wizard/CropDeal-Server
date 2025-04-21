package com.ayush.bank_account_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ayush.bank_account_service.exception.ResourceNotFoundException;
import com.ayush.bank_account_service.feign.FarmerInterface;
import com.ayush.bank_account_service.model.BankAccount;
import com.ayush.bank_account_service.repository.BankAccountRepository;
import com.ayush.bank_account_service.dto.BankAccountUpdationDto;

@Service
public class BankAccountService {

	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@Autowired
	private FarmerInterface farmerInterface;

	public ResponseEntity<BankAccount> createBankAccount(BankAccount bankAccount) {
		// Verify farmer exists
		farmerInterface.getFarmerById(bankAccount.getFarmer().getId());
		
		// Check if farmer already has a bank account
		if (bankAccountRepository.existsByFarmer_Id(bankAccount.getFarmer().getId())) {
			throw new IllegalStateException("Farmer already has a bank account");
		}
		
		BankAccount savedAccount = bankAccountRepository.save(bankAccount);
		return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
	}

	public ResponseEntity<BankAccount> getBankAccountById(Long id) {
		BankAccount account = bankAccountRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Bank account not found with ID: " + id));
		return new ResponseEntity<>(account, HttpStatus.OK);
	}

	public ResponseEntity<BankAccount> getBankAccountByFarmerId(Long farmerId) {
		BankAccount account = bankAccountRepository.findByFarmer_Id(farmerId)
			.orElseThrow(() -> new ResourceNotFoundException("Bank account not found for farmer ID: " + farmerId));
		return new ResponseEntity<>(account, HttpStatus.OK);
	}

	public ResponseEntity<BankAccount> updateBankAccount(Long id, BankAccountUpdationDto accountDto) {
		BankAccount existingAccount = bankAccountRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Bank account not found with ID: " + id));
			
		if (accountDto.getAccountNumber() != null) existingAccount.setAccountNumber(accountDto.getAccountNumber());
		if (accountDto.getIfscCode() != null) existingAccount.setIfscCode(accountDto.getIfscCode());
		if (accountDto.getAccountHolderName() != null) existingAccount.setAccountHolderName(accountDto.getAccountHolderName());

		BankAccount updatedAccount = bankAccountRepository.save(existingAccount);
		return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
	}

	public ResponseEntity<String> deleteBankAccount(Long id) {
		BankAccount account = bankAccountRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Bank account not found with ID: " + id));
			
		bankAccountRepository.delete(account);
		return new ResponseEntity<>("Bank account deleted successfully", HttpStatus.OK);
	}

	public ResponseEntity<String> deleteBankAccountByFarmerId(Long farmerId) {
		BankAccount account = bankAccountRepository.findByFarmer_Id(farmerId)
			.orElseThrow(() -> new ResourceNotFoundException("Bank account not found for farmer ID: " + farmerId));
			
		bankAccountRepository.delete(account);
		return new ResponseEntity<>("Bank account deleted successfully", HttpStatus.OK);
	}

	public ResponseEntity<List<BankAccount>> getAllBankAccounts() {
		List<BankAccount> accounts = bankAccountRepository.findAll();
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}
}
