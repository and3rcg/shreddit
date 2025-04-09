package com.example.shreddit.dto.request;

public record CommentRequestDTO(String text, Long authorId, Long postId) {
}
