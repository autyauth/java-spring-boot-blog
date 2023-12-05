package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
// This tells Spring that this class is a service, automatically registered as a bean so that it can be injected into other classes
public class PostServiceImpl implements PostService {
    private PostRepository postRepository; // PostRepository have all the methods to interact with the database

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        // TODO: Implement this method
        // This method should create a new post
        // The PostDto is converted to a Post entity and then saved to the database

        Post newPost = postRepository.save(mapToEntity(postDto)); // save the entity to the database and return the saved entity
        // convert the saved entity to DTO and return it
        return mapToDTO(newPost);
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostDto> response = posts.stream().map(this::mapToDTO).toList();
        return response;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id",id)
        );

        return mapToDTO(post);
    }

    private PostDto mapToDTO(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
}