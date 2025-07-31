package com.example.auction_application.UserModule.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.auction_application.Bid.entity.Bid;
import com.example.auction_application.Exceptions.UserAlreadyExistsException;
import com.example.auction_application.UserModule.UserRepository;
import com.example.auction_application.UserModule.dto.UserRequestDTO;
import com.example.auction_application.UserModule.dto.UserResponseDTO;
import com.example.auction_application.UserModule.entity.WebUser;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public String createUser(UserRequestDTO userRequestDTO){

        if(userRepository.findByUserEmail(userRequestDTO.getUserEmail()).isPresent()){
            throw new UserAlreadyExistsException("user with this email already exists.");
        }

        userRepository.save(userRequestDTO.getWebUser());
        return "successfully created a user";   
    }

    @Transactional
    public WebUser save(WebUser webUser){
        return userRepository.save(webUser);
    }

    public List<WebUser> getUsers(){
        return userRepository.findAll();
    }

    public List<Bid> getBidsByUserId(Long user_id){
        Optional<WebUser> webUser = userRepository.findById(user_id);
        if(webUser.isPresent()){
            return webUser.get().getBids();
        }
        return null;
    }    

    @Transactional
    public void deleteAllUsers(){
        userRepository.deleteAll();
    }

    @Transactional
    public String saveReqDTO(UserRequestDTO userRequestDTO) {        
        return createUser(userRequestDTO);
    }

    public WebUser findById(Long Id){
        return userRepository.findById(Id).get();
    }

    public Optional<WebUser> findByEmail(String email){
        return userRepository.findByUserEmail(email);
    }

}
