package com.example.shreddit.controller.v1;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
public class PostControllerV1 {

    @GetMapping
    public String getPosts() {
        return "Posts";
    }

    @PostMapping
    public String createPost() {
        return "Post created";
    }

    @PutMapping("/{id}")
    public String updatePost(@PathVariable Long id) {
        return "Post updated";
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        return "Post deleted";
    }
}
