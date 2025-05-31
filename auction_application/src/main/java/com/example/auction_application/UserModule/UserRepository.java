package com.example.auction_application.UserModule;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auction_application.UserModule.entity.WebUser;


public interface UserRepository extends JpaRepository<WebUser, Long>{
    public Optional<WebUser> findByUserName(String userName);
}
