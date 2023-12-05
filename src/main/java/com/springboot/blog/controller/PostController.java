package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;
    // Autowire are not needed here because we are using constructor injection instead of field injection
    // Autowire is not recommended because it makes the code tightly coupled
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create Post
    @PostMapping("")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
//        return postService.createPost(postDto);
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
}
