package com.example.shreddit.dto.response;

import com.example.shreddit.entity.Post;
import com.example.shreddit.entity.Vote;

import java.util.Date;
import java.util.List;

public record PostListResponseDTO(String title, String author, Date createdAt, int score) {
    public PostListResponseDTO(Post post) {
        this(post.getTitle(), post.getAuthor().getUsername(), post.getCreatedAt(), calculateScore(post.getVotes()));
    }

    public static int calculateScore(List<Vote> votes) {
        return votes.stream().mapToInt(vote -> vote.isPositive() ? 1 : -1).sum();
    }
}
