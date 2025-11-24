package com.example.provy.review.DTO;

import com.example.provy.review.Review;
import com.example.provy.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReviewDTOMapper {
    public Review toEntity(ReviewRequestDTO dto){
        CustomUserDetails currentUser = getCurrentUser();
        Review review = new Review();
        review.setProviderProfileId(dto.getProviderProfileId());
        review.setUserId(currentUser.getId());
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setCreatedAt(LocalDateTime.now());

        return review;
    }

    public ReviewResponseDTO toResponseDTO(Review review, String userName){
        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setId(review.getId());
        dto.setUserName(userName);
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        return dto;


    }

    public CustomUserDetails getCurrentUser(){
        return (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
