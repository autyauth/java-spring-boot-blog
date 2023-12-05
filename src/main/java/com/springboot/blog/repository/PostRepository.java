package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Do not add @Repository annotation here because JpaRepository already has it
// @Repository // This tells Spring that this interface is a repository
public interface PostRepository extends JpaRepository<Post, Long>{
    // Long is the type of the primary key of the Post entity

}
