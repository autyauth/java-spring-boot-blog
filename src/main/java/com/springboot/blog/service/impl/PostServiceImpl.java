package com.springboot.blog.service.impl;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // This tells Spring that this class is a service, automatically registered as a bean so that it can be injected into other classes
public class PostServiceImpl implements PostService{
    private PostRepository postRepository; // PostRepository have all the methods to interact with the database


    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    @Override
    public PostDto createPost(PostDto postDto) {
        // TODO: Implement this method
        // This method should create a new post
        // The PostDto is converted to a Post entity and then saved to the database

        // create a new Post entity then convert DTO to entity
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post newPost = postRepository.save(post); // save the entity to the database and return the saved entity

        // convert the saved entity to DTO and return it
        PostDto postResponse = new PostDto();
        postResponse.setId(newPost.getId());
        postResponse.setTitle(newPost.getTitle());
        postResponse.setContent(newPost.getContent());
        postResponse.setDescription((newPost.getDescription()));
        return postResponse;
    }
}
