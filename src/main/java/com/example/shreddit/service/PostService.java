package com.example.shreddit.service;

import com.example.shreddit.dto.request.PostRequestDTO;
import com.example.shreddit.dto.response.PostDetailsResponseDTO;
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

    public PostDetailsResponseDTO getPost(String slug) {
        Post postObj = repository.findBySlug(slug).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return new PostDetailsResponseDTO(postObj);
    }

    public PostDetailsResponseDTO createPost(PostRequestDTO post) {
        Post postObj = repository.save(new Post(post.title(), post.content()));
        return new PostDetailsResponseDTO(postObj);
    }

    public PostDetailsResponseDTO updatePost(PostRequestDTO request, String slug) {
        Post postObj = repository.findBySlug(slug).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        postObj.setTitle(request.title());
        postObj.setContent(request.content());
        repository.save(postObj);
        return new PostDetailsResponseDTO(postObj);
    }

    public void deletePost(String slug) {
        Post postObj = repository.findBySlug(slug).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        repository.delete(postObj);
    }
}
