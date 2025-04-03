package com.example.shreddit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Vote {
    @Id
    private Long id;

    private boolean isPositive;
    private String contentType;
    private Long contentId;
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public void setPositive(boolean positive) {
        isPositive = positive;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        if (!Objects.equals(contentType, "post") && !Objects.equals(contentType, "comment")) {
            throw new IllegalArgumentException("contentType must be 'post' or 'comment'");
        }

        this.contentType = contentType;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
