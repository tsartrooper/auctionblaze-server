package com.example.auction_application.Authentication;

public class AuthenticationRequestDTO {
    String userName;
    String password;

    public AuthenticationRequestDTO() {
    }

    public AuthenticationRequestDTO(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
