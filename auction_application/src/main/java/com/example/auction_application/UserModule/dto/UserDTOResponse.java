package com.example.auction_application.UserModule.dto;

import com.example.auction_application.UserModule.entity.WebUser;

public class UserDTOResponse {
    private Long id;
    private String userName;
    private String userEmail;

    public UserDTOResponse(WebUser webUser){
        this.id = webUser.getId();
        this.userEmail = webUser.getUserEmail();
        this.userName = webUser.getUserName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
