package com.techoble.reviewer.controller;

import com.techoble.reviewer.dto.CrewsDto;
import com.techoble.reviewer.dto.ReviewersDto;
import com.techoble.reviewer.service.ReviewerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Void> register(@RequestParam String name, @RequestParam String part) {
        reviewerService.add(name, part);
        return ResponseEntity.ok().build();
    }
//
//    @GetMapping("/reviewers")
//    public ResponseEntity<ReviewersDto> findReviewers() {
//        ReviewersDto reviewers = reviewerService.findReviewers();
//        if (reviewers.getReviewers().size() < 3) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(reviewers);
//    }
//
//    @PostMapping("/reviewers")
//    public ResponseEntity<ReviewersDto> shuffle() {
//        reviewerService.shuffle();
//        return ResponseEntity.ok().build();
//    }
}
