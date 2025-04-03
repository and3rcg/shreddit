package com.example.shreddit.controller.v1;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentControllerV1 {
    @PostMapping("/post/{postId}")
    public String createComment(@PathVariable Long postId) {
        return "Comment created";
    }

    @GetMapping("/post/{postId}")
    public String getComments(@PathVariable Long postId) {
        // Ordenar por contagem de votos (padrão) e dar possibilidade de ordenar por data de criação
        return "Comments";
    }
}
