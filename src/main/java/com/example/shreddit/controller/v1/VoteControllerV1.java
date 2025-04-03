package com.example.shreddit.controller.v1;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vote")
public class VoteControllerV1 {
    @PostMapping("/upvote")
    public String upvote() {
        return "Upvoted";
    }
}
