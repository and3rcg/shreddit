package com.example.shreddit.dto.response;

import com.example.shreddit.entity.Comment;
import com.example.shreddit.entity.Vote;

import java.util.Date;
import java.util.List;

public record CommentResponseDTO(String text, UserResponseDTO author, Date createdAt, Date updatedAt, int score) {
    public CommentResponseDTO(Comment comment) {
        this(comment.getText(), new UserResponseDTO(comment.getAuthor()), comment.getCreatedAt(), comment.getUpdatedAt(), calculateScore(comment.getVotes()));
    }

    public static int calculateScore(List<Vote> votes) {
        return votes.stream().mapToInt(vote -> vote.isPositive() ? 1 : -1).sum();
    }
}
