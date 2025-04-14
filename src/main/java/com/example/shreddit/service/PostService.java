package com.example.shreddit.service;

import com.example.shreddit.dto.request.PostRequestDTO;
import com.example.shreddit.dto.response.CommentResponseDTO;
import com.example.shreddit.dto.response.PostDetailsResponseDTO;
import com.example.shreddit.dto.response.PostListResponseDTO;
import com.example.shreddit.entity.Post;
import com.example.shreddit.entity.User;
import com.example.shreddit.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public PostService(PostRepository postRepository, CommentService commentService, UserService userService) {
        this.postRepository = postRepository;
        this.commentService = commentService;
        this.userService = userService;
    }

    public List<PostListResponseDTO> getPosts() {
        return postRepository.findAll().stream().map(PostListResponseDTO::new).toList();
    }

    public PostDetailsResponseDTO getPost(String slug) {
        Post postObj = postRepository.findBySlug(slug).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return new PostDetailsResponseDTO(postObj);
    }

    public PostDetailsResponseDTO createPost(PostRequestDTO post, String username) {
        System.out.println(username);
        User author = userService.findUserByUsername(username);
        Post postObj = postRepository.save(new Post(post.title(), post.content(), author));
        return new PostDetailsResponseDTO(postObj);
    }

    public PostDetailsResponseDTO updatePost(PostRequestDTO request, String slug) {
        Post postObj = postRepository.findBySlug(slug).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        postObj.setTitle(request.title());
        postObj.setContent(request.content());
        postRepository.save(postObj);
        return new PostDetailsResponseDTO(postObj);
    }

    public void deletePost(String slug) {
        Post postObj = postRepository.findBySlug(slug).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        postRepository.delete(postObj);
    }

    public List<CommentResponseDTO> getCommentsByPost(String slug) {
        Post postObj = postRepository.findBySlug(slug).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return commentService.getCommentsByPost(postObj).stream().map(CommentResponseDTO::new).toList();
    }
}
