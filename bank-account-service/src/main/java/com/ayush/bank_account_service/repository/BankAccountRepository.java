package com.ayush.bank_account_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ayush.bank_account_service.model.BankAccount;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

  boolean existsByFarmer_Id(Long id);

  Optional<BankAccount> findByFarmer_Id(Long farmerId);

//	
	
}
