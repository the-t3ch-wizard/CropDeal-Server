package com.ayush.review_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ayush.review_service.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByFarmer_Id(Long farmerId);
    List<Review> findByDealer_Id(Long dealerId);
}
