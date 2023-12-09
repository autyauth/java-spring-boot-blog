package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
//        return postService.createPost(postDto);
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
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
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable(name = "id") Long id){
        return new ResponseEntity<>(postService.updatePost(postDto, id), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id")Long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }
}
