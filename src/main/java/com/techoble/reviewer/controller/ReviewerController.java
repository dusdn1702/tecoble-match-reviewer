package com.techoble.reviewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReviewerController {

    @GetMapping
    public String crews() {
        return "crews.html";
    }
}
