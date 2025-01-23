package com.example.auction_application.Bid;

import java.time.LocalDateTime;

import com.example.auction_application.AuctionListing.AuctionListing;
import com.example.auction_application.UserModule.WebUser;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Bid {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private double amount;
    private LocalDateTime timeStamp;

    @ManyToOne
    private AuctionListing auctionListing;

    @ManyToOne
    private WebUser bidder;

    public Bid(){}

    public Bid(Long id, WebUser bidder, double amount){
        this.amount = amount;
        this.id = id;
        this.bidder = bidder;
        this.timeStamp = LocalDateTime.now();

        System.out.println("Bid object timestamp: "+timeStamp);
    }

    public Long getId() {
        return id;
    }

    public WebUser getBidder() {
        return bidder;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }    

    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setAuctionListing(AuctionListing auctionListing) {
        this.auctionListing = auctionListing;
    }

    public void setBidder(WebUser bidder) {
        this.bidder = bidder;
    }

    public AuctionListing getAuctionListing() {
        return auctionListing;
    }    
}
