package com.springboot.blog.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data // This annotation is from Lombok, it generates getters and setters for the fields in this class when the project is compiled
public class PostDto {
    private long id;
    // not null // at least 2 characters
    @NotNull(message = "Title cannot be null")
    @Size(min = 2, message = "Title must be at least 2 characters long")
    private String title;
    // not null // at least 2 characters
    @NotNull(message = "Description cannot be null")
    @Size(min = 2, message = "Description must be at least 2 characters long")
    private String description;
    // not null
    @NotNull(message = "Content cannot be null")
    private String content;
    private Set<CommentDto> comments;

}
