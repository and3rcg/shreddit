package com.example.shreddit.dto.response;

import com.example.shreddit.entity.Comment;
import com.example.shreddit.entity.Post;

import java.util.List;

// response object for the /me endpoint
public record UserMeResponseDTO(String email, String username, List<Post> posts, List<Comment> comments) {
    public UserMeResponseDTO(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.posts = user.getPosts();
        this.comments = user.getComments();
    }
}
