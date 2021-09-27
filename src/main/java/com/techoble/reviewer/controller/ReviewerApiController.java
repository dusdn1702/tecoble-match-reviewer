package com.techoble.reviewer.controller;

import com.techoble.reviewer.dto.CrewsDto;
import com.techoble.reviewer.dto.ReviewersDto;
import com.techoble.reviewer.service.ReviewerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReviewerApiController {

    private final ReviewerService reviewerService;

    public ReviewerApiController(final ReviewerService reviewerService) {
        this.reviewerService = reviewerService;
    }

    @GetMapping("/crews")
    public ResponseEntity<CrewsDto> findCrews() {
        CrewsDto crews = reviewerService.findCrews();
        return ResponseEntity.ok(crews);
    }

    @PostMapping("/crews")
    public ResponseEntity<Void> saveCrew(@RequestParam String name, @RequestParam String part) {
        reviewerService.saveCrew(name, part);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/crews")
    public ResponseEntity<Void> deleteAll() {
        reviewerService.deleteAllCrews();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reviewers")
    public ResponseEntity<ReviewersDto> findReviewers() {
        ReviewersDto reviewers = reviewerService.findReviewers();
        return ResponseEntity.ok(reviewers);
    }
}
