package com.example.shreddit.service;

import com.example.shreddit.dto.request.CommentRequestDTO;
import com.example.shreddit.dto.response.CommentResponseDTO;
import com.example.shreddit.entity.Comment;
import com.example.shreddit.entity.Post;
import com.example.shreddit.entity.User;
import com.example.shreddit.repository.CommentRepository;
import com.example.shreddit.repository.PostRepository;
import com.example.shreddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findByPost(post);
    }

    public CommentResponseDTO createComment(CommentRequestDTO request) {
        Post post = postRepository.findById(request.postId()).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        User author = userRepository.findById(request.authorId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Comment comment = new Comment(request.text(), author, post);
        commentRepository.save(comment);
        return new CommentResponseDTO(comment);
    }
}
