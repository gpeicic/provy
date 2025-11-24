package com.example.provy.review;

import com.example.provy.review.DTO.ReviewDTOMapper;
import com.example.provy.review.DTO.ReviewRequestDTO;
import com.example.provy.review.DTO.ReviewResponseDTO;
import com.example.provy.security.AuthorizationService;
import com.example.provy.user.User;
import com.example.provy.user.UserMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Primary
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDTOMapper reviewDTOMapper;
    private final ReviewMapper reviewMapper;
    private final UserMapper userMapper;


    public ReviewServiceImpl(ReviewDTOMapper reviewDTOMapper, ReviewMapper reviewMapper, UserMapper userMapper) {
        this.reviewDTOMapper = reviewDTOMapper;
        this.reviewMapper = reviewMapper;
        this.userMapper = userMapper;

    }

    @Override
    public ReviewResponseDTO createReview(ReviewRequestDTO dto) {
        Review review = reviewDTOMapper.toEntity(dto);
        reviewMapper.insertReview(review);
        User user = userMapper.getUserById(review.getUserId());
        return reviewDTOMapper.toResponseDTO(review, user.getIme()+" " + user.getPrezime());
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByProvider(Long providerProfileId) {
        List<Review> reviews = reviewMapper.getReviewsByProviderId(providerProfileId);

        return reviews.stream()
                .map(review -> {
                    String userName = userMapper.getUserById(review.getUserId()).getIme();
                    return reviewDTOMapper.toResponseDTO(review, userName);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReview(Long reviewId) {
        Review review = reviewMapper.getById(reviewId);
        AuthorizationService.authorizeCurrentUserOrAdmin(review.getUserId(), "You cannot delete this review");
        reviewMapper.deleteById(reviewId);
    }
}
