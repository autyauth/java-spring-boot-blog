package com.springboot.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private long id;
    // not empty
    // at least 2 characters
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String name;
    // not empty
    // valid email address
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;
    // not empty
    @NotEmpty(message = "Body cannot be empty")
    private String body;
}
