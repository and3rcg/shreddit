package com.example.shreddit.dto.response;

import com.example.shreddit.entity.Comment;
import com.example.shreddit.entity.Post;
import com.example.shreddit.entity.User;

import java.util.List;

// response object for the /me endpoint
public record UserMeResponseDTO(String email, String username, List<Post> posts, List<Comment> comments) {
    public UserMeResponseDTO(User user) {
        this(user.getEmail(), user.getUsername(), user.getPosts(), user.getComments());
    }
}
