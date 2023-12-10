package com.springboot.blog.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtTokenProvider jwtTokenProvider;
    private UserDetailsService userDetailsService;
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // This method is called for every request that comes to the server
        // This method is used to authenticate a user
        String jwt = getJwtFromRequest(request); // get the JWT token from the request
        if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) { // check if the token is valid
            String username = jwtTokenProvider.getUsernameFromJWT(jwt); // get the username from the token
            UserDetails userDetails = userDetailsService.loadUserByUsername(username); // load the user details from the database
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            ); // create an Authentication object
            // Authentication object is used by Spring security to check if a user has access to a resource
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication); // set the Authentication object to the SecurityContext

        }
        filterChain.doFilter(request, response); // pass the request and response to the next filter
    }
    private String getJwtFromRequest(HttpServletRequest request) { // HttpServletRequest is an interface that provides methods to get information from the request
        String bearerToken = request.getHeader("Authorization"); // get the Authorization header from the request
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // return the token without the Bearer prefix
        }
        return null;
    }
}
