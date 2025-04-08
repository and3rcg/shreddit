package com.example.shreddit.service;

import com.example.shreddit.dto.request.PostRequestDTO;
import com.example.shreddit.dto.response.PostListResponseDTO;
import com.example.shreddit.entity.Post;
import com.example.shreddit.entity.User;
import com.example.shreddit.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository repository;

    @Autowired
    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<PostListResponseDTO> getPosts() {
        return repository.findAll().stream().map(post -> new PostListResponseDTO(post)).toList();
    }

    public Post getPost(String slug) {
        return repository.findBySlug(slug).orElseThrow(() -> new IllegalArgumentException("Post not found"));
    }

    public Post createPost(PostRequestDTO post, User author) {
        return repository.save(new Post(post.title(), post.content(), author));
    }
}
