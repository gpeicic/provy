package com.example.provy.review.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ReviewRequestDTO {
    @NotNull
    private Long providerProfileId;
    @NotNull
    @Min(1) @Max(5)
    private Integer rating;
    private String comment;

    public ReviewRequestDTO(@NotNull Long providerProfileId, @NotNull Integer rating, String comment) {
        this.providerProfileId = providerProfileId;
        this.rating = rating;
        this.comment = comment;
    }

    public Long getProviderProfileId() {
        return providerProfileId;
    }

    public void setProviderProfileId(Long providerProfileId) {
        this.providerProfileId = providerProfileId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
