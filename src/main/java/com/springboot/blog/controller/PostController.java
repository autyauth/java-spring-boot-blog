package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @RequestHeader("Authorization") String token) {
//        return postService.createPost(postDto);
        System.out.println(token);
        return new ResponseEntity<>(postService.createPost(postDto, token), HttpStatus.CREATED);
    }
//    @GetMapping
//    public ResponseEntity<List<PostDto>> getAllPosts(
//            @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "10") int size
//    ) {
//        return new ResponseEntity<>(postService.getAllPosts(page, size), HttpStatus.OK);
//    }

@GetMapping
public ResponseEntity<PostResponse> getAllPosts(
        @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
        @RequestParam(name = "pagesize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int size,
        @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = AppConstants.DEFAULT_SORT_ORDER, required = false) String sortOrder
) {
    return new ResponseEntity<>(postService.getAllPosts(page, size, sortBy, sortOrder), HttpStatus.OK);
}
    @GetMapping ("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id) {
//        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
        return ResponseEntity.ok(postService.getPostById(id));
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable(name = "id") Long id){
        return new ResponseEntity<>(postService.updatePost(postDto, id), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id")Long id,
                                                 @RequestHeader("Authorization")String token){
        postService.deletePostById(id, token);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }
}
