package com.springboot.blog.payload;

import lombok.Data;

import java.util.Set;

@Data // This annotation is from Lombok, it generates getters and setters for the fields in this class when the project is compiled
public class PostDto {
    private long id;
    private String title;
    private String description;
    private String content;
    private Set<CommentDto> comments;

}
