package com.example.shreddit.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.Contract;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: para a versão final, precisaremos de um relacionamento entre Post e SubShreddit

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(unique = true, nullable = false, updatable = false)
    private String slug;

    // you gotta specify the name of the column to join, and don't forget to add OneToMany in the target entity (User in this case)
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = true, referencedColumnName = "id", updatable = false)
    private User author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Vote> votes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Report> reports;

    @CreationTimestamp
    private Date createdAt;

    protected Post() {}  // Needed for Hibernate

    public Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
        setSlug();
    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
        setSlug();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSlug() {
        return slug;
    }

    // generates a random suffix and appends it to the slugified title
    public void setSlug() {
        String randomSuffix = "-" + UUID.randomUUID().toString().substring(0, 5).toLowerCase();
        this.slug = title.replace(" ", "-").toLowerCase() + randomSuffix;
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

    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", slug='" + slug + '\'' +
                ", authorId=" + author.getId() +
                '}';
    }

    // it's necessary to ensure that Object o is of the type you expect, otherwise you will get a ClassCastException
    // That's the reason I used @Contract. if the argument is null, automatically return false. you can also check that
    @Override
    @Contract("null -> false")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(this.slug, (post.slug));
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}