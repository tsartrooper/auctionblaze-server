package com.example.auction_application.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.auction_application.UserModule.UserRepository;
import com.example.auction_application.UserModule.entity.WebUser;
import com.example.auction_application.UserModule.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email){
        WebUser user = userService.findByEmail(email).orElse(null);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return User.builder()
                    .username(email)
                    .password(user.getPassword() != null? user.getPassword() : "")
                    .roles(user.getRole().replace("ROLE_", ""))
                    .build();
    }

    public WebUser getUserByUsername(String userEmail){
        WebUser user = userService.findByEmail(userEmail).get();
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}
