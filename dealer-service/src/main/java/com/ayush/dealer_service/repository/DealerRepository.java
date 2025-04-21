package com.ayush.dealer_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ayush.dealer_service.model.Dealer;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, Long> {

  List<Dealer> findByStatus(String status);

//	
	
}
