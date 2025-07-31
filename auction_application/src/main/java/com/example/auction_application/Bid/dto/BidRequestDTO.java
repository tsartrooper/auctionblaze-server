package com.example.auction_application.Bid.dto;

import java.time.LocalDateTime;

public class BidRequestDTO {
    private double amount;
    private LocalDateTime timeStamp;
    private Long auctionListingId;

    public BidRequestDTO(double amount, Long auctionListingId){
        this.amount = amount;
        this.timeStamp = LocalDateTime.now();
        this.auctionListingId = auctionListingId;
    }
    public BidRequestDTO(){}

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

    public Long getAuctionListingId() {
        return auctionListingId;
    }

    public void setAuctionListingId(Long auctionListingId) {
        this.auctionListingId = auctionListingId;
    }
}
