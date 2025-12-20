package com.example.auction_application.UserModule.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.auction_application.AuctionListing.entity.AuctionListing;
import com.example.auction_application.Bid.entity.Bid;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class WebUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = false, nullable = false)
    private String userName;

    @Column(unique = true, nullable = false)
    private String userEmail;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true)
    private String picture;

    @Column(nullable = true)
    private String authProvider;

    @Column(nullable = false)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "bidder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bid> bids = new ArrayList<>();

    @OneToMany(mappedBy="seller", cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AuctionListing> sellingAuctions;

    @OneToMany(mappedBy="currentHighestBidder", cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AuctionListing> biddingAuctions;

    public WebUser() {
    }

    public WebUser(String userName, String userEmail, String password, String role) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
        this.roles = new ArrayList<>();
        this.roles.add(role);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    
    public void addRole(String role) {
        if(!this.roles.contains(role)) {
            this.roles.add(role);
        }
    }
    
    public void removeRole(String role) {
        this.roles.remove(role);
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public List<AuctionListing> getSellingAuctions() {
        return sellingAuctions;
    }

    public void setSellingAuctions(List<AuctionListing> sellingAuctions) {
        this.sellingAuctions = sellingAuctions;
    }

    public List<AuctionListing> getBiddingAuctions() {
        return biddingAuctions;
    }

    public void setBiddingAuctions(List<AuctionListing> biddingAuctions) {
        this.biddingAuctions = biddingAuctions;
    }

    public String getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(String authProvider) {
        this.authProvider = authProvider;
    }

}
