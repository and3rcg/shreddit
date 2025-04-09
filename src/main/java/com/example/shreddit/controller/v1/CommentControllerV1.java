package com.example.shreddit.controller.v1;

import com.example.shreddit.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentControllerV1 {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentControllerV1(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
}
