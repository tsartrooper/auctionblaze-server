package com.example.auction_application.UserModule;

import java.util.ArrayList;
import java.util.List;

import com.example.auction_application.AuctionListing.AuctionListing;
import com.example.auction_application.Bid.Bid;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.UniqueConstraint;

@Entity
public class WebUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(unique = true, nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;
    
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
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
}
