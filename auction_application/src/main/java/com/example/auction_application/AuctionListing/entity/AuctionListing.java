package com.example.auction_application.AuctionListing.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cglib.core.Local;

import com.example.auction_application.AuctionListing.Status;
import com.example.auction_application.Bid.entity.Bid;
import com.example.auction_application.UserModule.entity.WebUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity
public class AuctionListing {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double startingPrice;
    private String description;
    private String itemName;
    private LocalDateTime startTime;
    private double currentHighestBid;
    private LocalDateTime endTime;
    private String category;
    private String picture;

    @Enumerated(EnumType.STRING)
    private Status auctionStatus; 

    @ManyToOne
    private WebUser currentHighestBidder;

    @OneToMany(mappedBy = "auctionListing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bid> bids = new ArrayList<>();
    
    @ManyToOne
    private WebUser seller;    

    public AuctionListing() {
    }
    public AuctionListing(double startingPrice, String description, String itemName, WebUser seller, String category, LocalDateTime startTime, LocalDateTime endTime) {
        this.startingPrice = startingPrice;
        this.description = description;
        this.itemName = itemName;
        this.currentHighestBid = startingPrice;
        this.seller = seller;
        this.auctionStatus = Status.ACTIVE;
        this.currentHighestBidder = seller;
        this.endTime = endTime;
        this.category = category;
        this.startTime = startTime;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public double getStartingPrice() {
        return startingPrice;
    }
    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }
    public String getDescription() {
        return description;
    }
    public String getItemName() {
        return itemName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public double getCurrentHighestBid() {
        return currentHighestBid;
    }
    public void setCurrentHighestBid(double currentHighestBid) {
        this.currentHighestBid = currentHighestBid;
    }
    public WebUser getCurrentHighestBidder() {
        return currentHighestBidder;
    }
    public void setCurrentHighestBidder(WebUser currentHighestBidder) {
        this.currentHighestBidder = currentHighestBidder;
    }
    public WebUser getSeller() {
        return seller;
    }

    public void setSeller(WebUser seller) {
        this.seller = seller;
    }
    public List<Bid> getBids() {
        return bids;
    }
    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }
    public Status getAuctionStatus() {
        return auctionStatus;
    }
    public void setAuctionStatus(Status auctionStatus) {
        this.auctionStatus = auctionStatus;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }  
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    public void addBid(Bid bid) {
        this.bids.add(bid);
    }

}
