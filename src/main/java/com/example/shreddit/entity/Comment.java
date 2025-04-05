package com.example.shreddit.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = true)
    private User author;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<Vote> votes;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<Report> reports;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    protected Comment() {}  // necessary for Hibernate

    public Comment(String text, User author, Post post) {
        this.text = text;
        this.author = author;
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthorId() {
        return author;
    }

    public void setAuthorId(User author) {
        this.author = author;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public List<Report> getReports() {
        return reports;
    }
}
