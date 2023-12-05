package com.springboot.blog.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data // Lombok annotation to generate all the getters, setters, equals, hash, and toString methods, based on the fields
@AllArgsConstructor // Lombok annotation to generate the constructor with all the fields
@NoArgsConstructor // Lombok annotation to generate the constructor with no parameters
@Entity
@Table(
        name = "POST", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)

public class Post {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "content", nullable = false)
    private String content;

}
