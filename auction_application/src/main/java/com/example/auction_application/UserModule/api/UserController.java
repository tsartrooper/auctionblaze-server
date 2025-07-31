package com.example.auction_application.UserModule.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auction_application.Authentication.JwtUtils;
import com.example.auction_application.Bid.dto.BidResponseDTO;
import com.example.auction_application.UserModule.dto.UserResponseDTO;
import com.example.auction_application.UserModule.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @GetMapping
    public List<UserResponseDTO> getUsers(){
        return userService
                        .getUsers()
                        .stream()
                        .map(UserResponseDTO::new)
                        .collect(Collectors.toList());
    }

    @DeleteMapping
    public void deleteAllUsers(){
        userService.deleteAllUsers();
    }

    @GetMapping("/profile")
    public UserResponseDTO getBidsById(HttpServletRequest request) {

        String token = request.getHeader("Authorization");

        Long user_id = jwtUtils.extractUserId(token.substring(7));

        return new UserResponseDTO(userService.findById(user_id));
    }
}
