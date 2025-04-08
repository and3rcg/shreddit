package com.example.shreddit.service;

import com.example.shreddit.entity.Post;
import com.example.shreddit.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository repository;

    @Autowired
    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public Post getPost(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post not found"));
    }
}
