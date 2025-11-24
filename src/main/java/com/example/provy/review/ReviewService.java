package com.example.provy.review;

import com.example.provy.review.DTO.ReviewRequestDTO;
import com.example.provy.review.DTO.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {
    ReviewResponseDTO createReview(ReviewRequestDTO dto);
    List<ReviewResponseDTO> getReviewsByProvider(Long providerProfileId);
    public void deleteReview(Long reviewId);
}
