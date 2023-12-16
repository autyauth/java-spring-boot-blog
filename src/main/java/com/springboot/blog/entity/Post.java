//package com.springboot.blog.entity;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.HashSet;
//import java.util.Set;
//
//
//@Data // Lombok annotation to generate all the getters, setters, equals, hash, and toString methods, based on the fields
//@AllArgsConstructor // Lombok annotation to generate the constructor with all the fields
//@NoArgsConstructor // Lombok annotation to generate the constructor with no parameters
//@Entity
//@Table(
//        name = "post", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
//)
//
//public class Post {
//    @Id
//    @GeneratedValue(
//            strategy = GenerationType.IDENTITY
//    )
//    private Long id;
//    @Column(name = "title", nullable = false)
//    private String title;
//    @Column(name = "description", nullable = false)
//    private String description;
//    @Column(name = "content", nullable = false)
//    private String content;
//    // cascade = CascadeType.ALL means that any change happened on the Post entity should cascade to the Comment entity
//    // orphanRemoval = true means that if a comment is removed from the Post entity, it should be removed from the database
//    @OneToMany(mappedBy = "post", cascade=CascadeType.ALL, orphanRemoval = true) // mappedBy = "post" means that the Post entity is the owner of the relationship
//    private Set<Comment> comments = new HashSet<>();
//}
package com.springboot.blog.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
        name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
public class Post {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}