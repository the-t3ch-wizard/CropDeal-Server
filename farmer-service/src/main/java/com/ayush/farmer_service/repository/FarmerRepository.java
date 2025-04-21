package com.ayush.farmer_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ayush.farmer_service.model.Farmer;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Long> {
	
//	
	
}
