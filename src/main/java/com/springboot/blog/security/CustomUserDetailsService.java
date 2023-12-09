package com.springboot.blog.security;

import com.springboot.blog.entity.User;
import com.springboot.blog.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service // This tells Spring that this class is a service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // This method is used by Spring security to authenticate a user
        // Authentication filter gets the username and password from the request then create an Authentication object(Credentials)
        // pass the Authentication object to the Authentication manager
        // Authentication manager will call authenticate() method of the Authentication provider(OAuth2, LDAP, DAO, etc.)
        // Authentication provider will call loadUserByUsername() method of the UserDetailsService
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail)
        ); // find a user by username or email
        Set<GrantedAuthority> authorities = user.getRoles().stream().map(
                (role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet()); // get the roles of the user and convert them to a set of GrantedAuthority objects
        // get the roles of the user and convert them to a set of GrantedAuthority objects
        // GrantedAuthority is used by Spring security to check if a user has access to a resource
        // Spring security load the user's roles into the authorities field of the User object from the database
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
