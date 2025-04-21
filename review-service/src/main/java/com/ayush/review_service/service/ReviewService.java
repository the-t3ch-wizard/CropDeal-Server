package com.ayush.review_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ayush.review_service.exception.ResourceNotFoundException;
import com.ayush.review_service.feign.DealerInterface;
import com.ayush.review_service.feign.FarmerInterface;
import com.ayush.review_service.model.Review;
import com.ayush.review_service.repository.ReviewRepository;
import com.ayush.review_service.dto.ReviewUpdationDto;

import java.util.List;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private FarmerInterface farmerInterface;
	
	@Autowired
	private DealerInterface dealerInterface;

	public ResponseEntity<Review> createReview(Review review) {
		// Verify both farmer and dealer exist
		farmerInterface.getFarmerById(review.getFarmer().getId());
		dealerInterface.getDealerById(review.getDealer().getId());
		
		Review savedReview = reviewRepository.save(review);
		return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
	}

	public ResponseEntity<Review> getReviewById(Long id) {
		Review review = reviewRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Review not found with ID: " + id));
		return new ResponseEntity<>(review, HttpStatus.OK);
	}

	public ResponseEntity<List<Review>> getReviewsByFarmerId(Long farmerId) {
		// Verify farmer exists
		farmerInterface.getFarmerById(farmerId);
		List<Review> reviews = reviewRepository.findByFarmer_Id(farmerId);
		return new ResponseEntity<>(reviews, HttpStatus.OK);
	}

	public ResponseEntity<List<Review>> getReviewsByDealerId(Long dealerId) {
		// Verify dealer exists
		dealerInterface.getDealerById(dealerId);
		List<Review> reviews = reviewRepository.findByDealer_Id(dealerId);
		return new ResponseEntity<>(reviews, HttpStatus.OK);
	}

	public ResponseEntity<Review> updateReview(Long id, ReviewUpdationDto reviewDto) {
		Review existingReview = reviewRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Review not found with ID: " + id));
			
		if (reviewDto.getRating() != null) existingReview.setRating(reviewDto.getRating());
		if (reviewDto.getFeedback() != null) existingReview.setFeedback(reviewDto.getFeedback());

		Review updatedReview = reviewRepository.save(existingReview);
		return new ResponseEntity<>(updatedReview, HttpStatus.OK);
	}

	public ResponseEntity<String> deleteReview(Long id) {
		Review review = reviewRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Review not found with ID: " + id));
			
		reviewRepository.delete(review);
		return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
	}
	
	public ResponseEntity<List<Review>> getAllReview() {
		List<Review> reviews = reviewRepository.findAll();
		return new ResponseEntity<>(reviews, HttpStatus.OK);
	}
}
