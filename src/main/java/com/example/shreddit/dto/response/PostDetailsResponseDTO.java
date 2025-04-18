package com.example.shreddit.dto.response;

import com.example.shreddit.entity.Comment;
import com.example.shreddit.entity.Post;
import com.example.shreddit.entity.Vote;

import java.util.Date;
import java.util.List;

public record PostDetailsResponseDTO(
        String title,
        String content,
        Date createdAt,
        String slug,
        UserResponseDTO author,
        List<Comment> comments,
        int score
) {
    public PostDetailsResponseDTO(Post post) {
        this(
            post.getTitle(),
            post.getContent(),
            post.getCreatedAt(),
            post.getSlug(),
            new UserResponseDTO(post.getAuthor()),
            post.getComments(),
            calculateScore(post.getVotes()
            )
        );
    }

    public static int calculateScore(List<Vote> votes) {
        if (votes == null) {
            return 0;
        }
        return votes.stream().mapToInt(vote -> vote.isPositive() ? 1 : -1).sum();
    }
}
