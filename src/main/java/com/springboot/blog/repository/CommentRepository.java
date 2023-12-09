package com.springboot.blog.repository;

import com.springboot.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Long is the type of the primary key of the Comment entity
    // this method equals "SELECT * FROM comment WHERE post_id = :postId" or "SELECT * FROM Comment c WHERE c.post.id = :postId"
    List<Comment> findByPostId(Long postId); // This is a derived query method By JPA naming convention // this method will return all comments of a post



}
