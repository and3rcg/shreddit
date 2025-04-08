package com.example.shreddit.controller.v1;

import com.example.shreddit.dto.request.PostRequestDTO;
import com.example.shreddit.dto.response.PostDetailsResponseDTO;
import com.example.shreddit.dto.response.PostListResponseDTO;
import com.example.shreddit.entity.Post;
import com.example.shreddit.entity.User;
import com.example.shreddit.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostControllerV1 {
    private final PostService postService;

    @Autowired
    public PostControllerV1(PostService postService) {
        this.postService = postService;
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
    public PostDetailsResponseDTO createPost(@RequestBody PostRequestDTO request) {
        // TODO: pegar o usuário logado e enviar ele ao PostService. por enquanto está como usuário nulo
        return postService.createPost(request);
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
}
