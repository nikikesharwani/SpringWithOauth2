package com.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import com.security.model.CustomUserDetails;
import com.security.model.Role;
import com.security.model.User;
import com.security.respository.UserRepository;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository repo) throws Exception {
    	if(repo.count() == 0)
    		repo.save(new User("user","user", Arrays.asList(new Role("USER"),new Role("ACTUATOR"))));
    	builder.userDetailsService(s -> new CustomUserDetails (repo.findByUsername(s)));
    }
}
