package com.ayush.review_service.controller;

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

import com.ayush.review_service.dto.ReviewUpdationDto;
import com.ayush.review_service.model.Review;
import com.ayush.review_service.service.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	ReviewService reviewService;
	
	@PostMapping
	public ResponseEntity<Review> createReview(@RequestBody Review review){
		return reviewService.createReview(review);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Review>> getAllReview(){
		return reviewService.getAllReview();
	}
	
	@GetMapping
	public ResponseEntity<Review> getReviewById(@RequestParam Long id){
		return reviewService.getReviewById(id);
	}
	
	@PutMapping
	public ResponseEntity<Review> updateReview(@RequestParam Long id, @RequestBody ReviewUpdationDto reviewDto){
		return reviewService.updateReview(id, reviewDto);
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteReviewById(@RequestParam Long id){
		return reviewService.deleteReview(id);
	}
	
}
