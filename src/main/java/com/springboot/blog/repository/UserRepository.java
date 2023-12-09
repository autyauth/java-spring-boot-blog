package com.springboot.blog.repository;

import com.springboot.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email); // Optional is a container object which may or may not contain a non-null value.
    // If a value is present, isPresent() will return true and get() will return the value.
    Optional<User> findByUsernameOrEmail(String username, String email); // this method will return a user by username or email
    Optional<User> findByUsername(String username); // this method will return a user by username
    Boolean existsByUsername(String username); // this method will return true if a user exists by username
    Boolean existsByEmail(String email); // this method will return true if a user exists by email
}
