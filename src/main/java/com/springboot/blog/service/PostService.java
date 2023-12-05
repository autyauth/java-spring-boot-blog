package com.springboot.blog.service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    List<PostDto> getAllPosts(int pageNo, int pageSize);
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto, long id);
    void deletePostById(Long id);
}
