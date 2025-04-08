package com.example.shreddit.entity;

import jakarta.persistence.*;

import java.util.Objects;



@Entity
@Table(name = "votes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "post_id"}),
        @UniqueConstraint(columnNames = {"user_id", "comment_id"})
    })
public class Vote {
    @Id
    private Long id;

    @Column(name = "is_positive", nullable = false)
    private boolean isPositive;

    // I'll overload the constructor. Both posts and comments can be voted
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = true)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = true)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    protected Vote() {}  // Needed for Hibernate

    public Vote(boolean isPositive, Post post, User user) {
        this.isPositive = isPositive;
        this.post = post;
        this.user = user;
    }

    public Vote(boolean isPositive, Comment comment, User user) {
        this.isPositive = isPositive;
        this.comment = comment;
        this.user = user;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
