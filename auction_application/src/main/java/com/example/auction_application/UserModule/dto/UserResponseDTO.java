package com.example.auction_application.UserModule.dto;

import com.example.auction_application.UserModule.entity.WebUser;

public class UserResponseDTO {
    private Long id;
    private String userName;
    private String userEmail;
    private String picture;

    public UserResponseDTO(WebUser webUser){
        this.id = webUser.getId();
        this.userEmail = webUser.getUserEmail();
        this.userName = webUser.getUserName();
        this.picture = webUser.getPicture();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
