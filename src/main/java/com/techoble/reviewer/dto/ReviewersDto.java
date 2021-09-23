package com.techoble.reviewer.dto;

import java.util.List;

public class ReviewersDto {

    private final List<String> reviewers;

    public ReviewersDto(List<String> reviewers) {
        this.reviewers = reviewers;
    }

    public List<String> getReviewers() {
        return reviewers;
    }
}
