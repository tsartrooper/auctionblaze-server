package com.example.auction_application.Bid.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.example.auction_application.Bid.entity.Bid;

public class BidResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private double amount;
    private LocalDateTime timeStamp;
    private Long bidderId;
    private Long auctionListingId;

    public BidResponseDTO(Bid bid){
        this.amount = bid.getAmount();
        this.auctionListingId = bid.getAuctionListing().getId();
        this.bidderId = bid.getBidder().getId();
        this.timeStamp = bid.getTimeStamp();   
        
        System.out.println("bid timestamp: "+this.getTimeStamp());
    }

    public BidResponseDTO(){}

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Long getBidderId() {
        return bidderId;
    }

    public void setBidderId(Long bidderId) {
        this.bidderId = bidderId;
    }

    public Long getAuctionListingId() {
        return auctionListingId;
    }

    public void setAuctionListingId(Long auctionListingId) {
        this.auctionListingId = auctionListingId;
    }
}
