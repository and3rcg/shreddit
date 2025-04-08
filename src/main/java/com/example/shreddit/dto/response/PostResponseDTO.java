package com.example.shreddit.dto.response;

import com.example.shreddit.entity.Post;
import com.example.shreddit.entity.Vote;

import java.util.List;

public record PostResponseDTO(String title, String content, String slug, UserResponseDTO author, int score) {
    public PostResponseDTO(Post post) {
        this(post.getTitle(), post.getContent(), post.getSlug(), new UserResponseDTO(post.getAuthor()), calculateScore(post.getVotes()));
    }

    public static int calculateScore(List<Vote> votes) {
        return votes.stream().mapToInt(vote -> vote.isPositive() ? 1 : -1).sum();
    }
}
