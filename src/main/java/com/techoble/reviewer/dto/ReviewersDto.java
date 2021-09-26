package com.techoble.reviewer.dto;

import java.util.List;

public class ReviewersDto {
    private List<String> backendReviewers;
    private List<String> frontendReviewers;

    public ReviewersDto() {
    }

    public ReviewersDto(List<String> backendReviewers, List<String> frontendReviewers) {
        this.backendReviewers = backendReviewers;
        this.frontendReviewers = frontendReviewers;
    }

    public List<String> getBackendReviewers() {
        return backendReviewers;
    }

    public List<String> getFrontendReviewers() {
        return frontendReviewers;
    }
}
