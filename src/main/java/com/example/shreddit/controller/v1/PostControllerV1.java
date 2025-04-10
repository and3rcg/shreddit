package com.example.shreddit.controller.v1;

import com.example.shreddit.dto.request.CommentRequestDTO;
import com.example.shreddit.dto.request.PostRequestDTO;
import com.example.shreddit.dto.response.CommentResponseDTO;
import com.example.shreddit.dto.response.PostDetailsResponseDTO;
import com.example.shreddit.dto.response.PostListResponseDTO;
import com.example.shreddit.service.CommentService;
import com.example.shreddit.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostControllerV1 {
    private final PostService postService;
    private final CommentService commentService;

    @Autowired
    public PostControllerV1(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping
    public List<PostListResponseDTO> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/{slug}")
    public PostDetailsResponseDTO getPost(@PathVariable String slug) {
        return postService.getPost(slug);
    }

    @PostMapping
    public ResponseEntity<PostDetailsResponseDTO> createPost(@RequestBody PostRequestDTO request, @AuthenticationPrincipal UserDetails userDetails) {
        PostDetailsResponseDTO post = postService.createPost(request, userDetails.getUsername());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.slug())
                .toUri();
        return ResponseEntity.created(location).body(post);
    }

    @PutMapping("/{slug}")
    public PostDetailsResponseDTO updatePost(@PathVariable String slug, @RequestBody PostRequestDTO request) {
        // TODO: adicionar lógica para checar se o usuário logado é admin ou o autor do post para poder editar
        return postService.updatePost(request, slug);
    }

    @DeleteMapping("/{slug}")
    public String deletePost(@PathVariable String slug) {
        // TODO: adicionar lógica para checar se o usuário logado é admin ou o autor do post para poder editar
        postService.deletePost(slug);
        return "Post deleted";
    }

    @GetMapping("{slug}/comments")
    public List<CommentResponseDTO> getComments(@PathVariable String slug) {
        return postService.getCommentsByPost(slug);
    }

    @PostMapping("/{slug}/comments")
    public CommentResponseDTO createComment(@PathVariable Long slug, @RequestBody CommentRequestDTO request) {
        return commentService.createComment(request);
    }

    // TODO: adicionar controllers PUT e DELETE para comentários
}
