package com.example.shreddit.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

/*
* how this entity works:
*   1. an user (author) can report either a comment or a post or another user (offender)
*   2. a report must have a reason
*   3. a report must have an author
*   4. a report must have an offender
*   5. if either a comment or a post is reported, the offender is the author of the comment or the author of the post
*   6. once action has been taken at the report, the report is marked as resolved
* */
@Entity
public class Report {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    private User author;

    @Column(name = "reason", nullable = false)
    private String reason;

    @ManyToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "id", nullable = true)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = true)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "offender_id", referencedColumnName = "id", nullable = false)
    private User offender;

    @Column(name = "is_resolved", nullable = false)
    private boolean isResolved = false;

    @CreationTimestamp
    private Date createdAt;

    public Report() {}  // necessary for Hibernate

    public Report(User author, String reason, Comment comment) {
        this.author = author;
        this.reason = reason;
        this.comment = comment;
        this.offender = comment.getAuthor();
    }

    public Report(User author, String reason, Post post) {
        this.author = author;
        this.reason = reason;
        this.post = post;
        this.offender = post.getAuthor();
    }

    public Report(User author, String reason, User offender) {
        this.author = author;
        this.reason = reason;
        this.offender = offender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getOffender() {
        return offender;
    }

    public void setOffender(User offender) {
        this.offender = offender;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public boolean isResolved() {
        return isResolved;
    }

    public void setResolved(boolean resolved) {
        isResolved = resolved;
    }
}
