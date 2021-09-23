package com.techoble.reviewer.controller;

import com.techoble.reviewer.dto.CrewsDto;
import com.techoble.reviewer.service.ReviewerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReviewerController {

    private final ReviewerService reviewerService;

    public ReviewerController(final ReviewerService reviewerService) {
        this.reviewerService = reviewerService;
    }

    @GetMapping("/crews")
    public String crews() {
        return "crews.html";
    }

    @GetMapping("/api/crews")
    public ResponseEntity<CrewsDto> findCrews() {
        CrewsDto crews = reviewerService.findCrews();
        if (crews.getCrews().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(crews);
    }

    @PostMapping("/api/crews")
    public ResponseEntity<Void> register(@RequestParam String name) {
        reviewerService.add(name);
        return ResponseEntity.ok().build();
    }
}
