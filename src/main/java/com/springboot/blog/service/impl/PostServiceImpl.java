package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.service.PostService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
// This tells Spring that this class is a service, automatically registered as a bean so that it can be injected into other classes
public class PostServiceImpl implements PostService {
    private PostRepository postRepository; // PostRepository have all the methods to interact with the database
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private ModelMapper mapper; // ModelMapper is used to map between DTO and entity, automatically injected by Spring

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto, String token) {
        // TODO: Implement this method
        // This method should create a new post
        // The PostDto is converted to a Post entity and then saved to the database
        String username = jwtTokenProvider.getUsernameFromJWT(token.substring(7));
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username. " + username)
        );
        Post post = mapToEntity(postDto);
        post.setUser(user);
        Post newPost = postRepository.save(post); // save the entity to the database and return the saved entity
        // convert the saved entity to DTO and return it
        return mapToDTO(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        // create a Pageable object
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

//        List<Post> posts = postRepository.findAll(); change to below
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> postList = posts.getContent();
        List<PostDto> postDtoList = postList.stream().map(this::mapToDTO).toList();
        PostResponse postResponse = new PostResponse(
                postDtoList,
                posts.getNumber(),
                posts.getSize(),
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.isLast()
        );
        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id",id)
        );
        return mapToDTO(post);
    }
    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", id)
                );

        setPost(postDto, post);
        Post updatePost = postRepository.save(post);
        return mapToDTO(updatePost);
    }

    @Override
    public void deletePostById(Long id, String token){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post","id",id)
        );
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        if (!post.getUser().getUsername().equals(username)){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"This is not your post,Can not Delete");
        }
        System.out.println("PostServiceImpl.deletePostById: " + post);
        postRepository.delete(post);
    }
    private void setPost(PostDto postDto, Post post){
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
    }

    private PostDto mapToDTO(Post post) {
        return mapper.map(post, PostDto.class);
    }

    private Post mapToEntity(PostDto postDto) {
        return mapper.map(postDto, Post.class);
    }
}
