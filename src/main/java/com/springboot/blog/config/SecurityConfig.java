package com.springboot.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean // This annotation is used to create a bean of SecurityFilterChain
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // This method is used to configure the HttpSecurity object
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests( // This method is used to authorize the requests from the client
                (authorize) ->
//                        authorize.anyRequest().authenticated() // This method is used to authorize all the requests from the client
                    authorize.requestMatchers(HttpMethod.GET,"/api/**").permitAll() // This method is used to authorize the GET requests from the client
                            .anyRequest().authenticated() // This method is used to authorize all the requests from the client
                ).httpBasic(Customizer.withDefaults());// This method is used to enable the HTTP Basic authentication
        // httpbasic is a method provided by Spring Security to enable the HTTP Basic authentication
        // Customizer.withDefaults() is a method provided by Spring Security to set the default values for the HTTP Basic authentication
        // csrf is a method provided by Spring Security to disable the Cross-Site Request Forgery
        // attacker can send a request to the server on behalf of the user
        // like <img src="http://localhost:8080/api/posts/1/comments/1" width="0" height="0">
        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("nakorn1")
                .password(passwordEncoder().encode("nakorn1"))
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin); // This class is used to create a user in the memory
    }


}
