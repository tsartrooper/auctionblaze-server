package com.example.auction_application.UserModule.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auction_application.Bid.dto.BidResponseDTO;
import com.example.auction_application.UserModule.dto.UserDTOResponse;
import com.example.auction_application.UserModule.service.UserService;

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
    UserService userService;

    @GetMapping
    public List<UserDTOResponse> getUsers(){
        return userService
                        .getUsers()
                        .stream()
                        .map(UserDTOResponse::new)
                        .collect(Collectors.toList());
    }

    @DeleteMapping
    public void deleteAllUsers(){
        userService.deleteAllUsers();
    }

    @GetMapping("/{user_id}")
    public List<BidResponseDTO> getBidsById(@PathVariable(name="user_id") Long user_id) {
        return userService
                        .getBidsByUserId(user_id)
                        .stream()
                        .map(BidResponseDTO::new)
                        .collect(Collectors.toList());
    }
}
