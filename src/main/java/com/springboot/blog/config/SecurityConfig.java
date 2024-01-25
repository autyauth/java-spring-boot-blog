package com.springboot.blog.config;

import com.springboot.blog.security.JwtAuthenticationEntryPoint;
import com.springboot.blog.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private UserDetailsService userDetailsService; // load the user details from the database and check authorization
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint; // set entry point for authentication
    private JwtAuthenticationFilter jwtAuthenticationFilter; // authenticate a user
    public SecurityConfig(UserDetailsService userDetailsService,
                          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager(); // This method is used to get the AuthenticationManager object
        // AuthenticationManager is used to authenticate a user by username and password
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // This method is used to configure the HttpSecurity object
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests( // used to authorize the requests from the client,
                (authorize) ->
//                        authorize.anyRequest().authenticated() // This method is used to authorize all the requests from the client
                    authorize.requestMatchers(HttpMethod.GET,"/api/**").permitAll() // Method Get will be allowed to access without authentication
                            .requestMatchers("/api/auth/**").permitAll() // auth will be allowed to access without authentication
                            //.requestMatchers(HttpMethod.POST,"/api/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                            .anyRequest().authenticated() // This method is used to authorize all the requests from the client
                ).exceptionHandling(
                        (exception) -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint) // This method is used to set the entry point for authentication
        ).sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); 
        // STATELESS means that the server will not create a session for the client
        // csrf is a method provided by Spring Security to disable the Cross-Site Request Forgery
        // attacker can send a request to the server on behalf of the user
        // like <img src="http://localhost:8080/api/posts/1/comments/1" width="0" height="0">

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // This method is used to add a filter before the UsernamePasswordAuthenticationFilter

        return http.build();
    }
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder()
//                .username("nakorn1")
//                .password(passwordEncoder().encode("nakorn1"))
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin); // This class is used to create a user in the memory
//    }


}
