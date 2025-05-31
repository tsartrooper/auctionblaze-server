package com.example.auction_application.Authentication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auction_application.UserModule.dto.UserRequestDTO;
import com.example.auction_application.UserModule.entity.WebUser;
import com.example.auction_application.UserModule.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/register")
    public String createUser(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.createUser(userRequestDTO);               
    }   

    @PostMapping("/login")
    public String login(@RequestBody AuthenticationRequestDTO authRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        
        WebUser user = userService.findByUserName(authRequest.getUserName());

        return jwtUtils.generateToken(authRequest.getUserName(), user.getId());        
    }


}
