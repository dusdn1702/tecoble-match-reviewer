package com.techoble.reviewer.controller;

import com.techoble.reviewer.domain.CrewsDto;
import com.techoble.reviewer.service.MatchingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MatchingController {
    private final MatchingService matchingService;

    public MatchingController(final MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    @GetMapping("/crews")
    public String crews() {
        return "crews.html";
    }

    @GetMapping("/api/crews")
    public ResponseEntity<CrewsDto> findCrews() {
        CrewsDto crews = matchingService.findCrews();
        if (crews.getCrews().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(crews);
    }

    @PostMapping("/api/crews")
    public ResponseEntity<Void> register(@RequestParam String name) {
        matchingService.add(name);
        return ResponseEntity.ok().build();
    }
}
