package com.example.shreddit.entity;

import jakarta.persistence.*;
import org.springframework.lang.Contract;

import java.util.List;
import java.util.Objects;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: para a versão final, precisaremos de um relacionamento entre Post e SubShreddit

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(unique = true, nullable = false)
    private String slug;

    // you gotta specify the name of the column to join, and don't forget to add OneToMany in the target entity (User in this case)
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = true, referencedColumnName = "id")
    private User author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Vote> votes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Report> reports;

    protected Post() {}  // Needed for Hibernate

    public Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
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

    public void setSlug() {
        this.slug = title.replace(" ", "-").toLowerCase();
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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
}