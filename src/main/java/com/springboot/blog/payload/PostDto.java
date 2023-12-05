package com.springboot.blog.payload;

import lombok.Data;

@Data // This annotation is from Lombok, it generates getters and setters for the fields in this class when the project is compiled
public class PostDto {
    private long id;
    private String title;
    private String description;
    private String content;

}
