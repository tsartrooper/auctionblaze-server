package com.example.auction_application.UserModule.dto;

import com.example.auction_application.UserModule.entity.WebUser;

public class UserRequestDTO {
    private String userName;
    private String userEmail;
    private String password;

    public UserRequestDTO() {
    }

    public UserRequestDTO(String userName, String userEmail, String password) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
    }

    public WebUser getWebUser(){
        return new WebUser(this.userName, this.userEmail, "{noop}"+this.password, "ROLE_USER");
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
