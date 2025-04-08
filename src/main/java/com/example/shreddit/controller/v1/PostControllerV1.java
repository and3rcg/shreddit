package com.example.shreddit.controller.v1;

import com.example.shreddit.dto.response.PostListResponseDTO;
import com.example.shreddit.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostControllerV1 {
    private final PostService postService;

    @Autowired
    public PostControllerV1(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostListResponseDTO> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/{slug}")
    public String getPost(@PathVariable String slug) {
        return "Post";
    }

    @PostMapping
    public String createPost() {
        return "Post created";
    }

    @PutMapping("/{slug}")
    public String updatePost(@PathVariable String slug) {
        return "Post updated";
    }

    @DeleteMapping("/{slug}")
    public String deletePost(@PathVariable String slug) {
        return "Post deleted";
    }
}
