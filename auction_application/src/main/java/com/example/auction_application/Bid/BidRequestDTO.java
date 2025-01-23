package com.example.auction_application.Bid;

import java.time.LocalDateTime;

public class BidRequestDTO {
    private double amount;
    private LocalDateTime timeStamp;
    private Long bidderId;
    private Long auctionListingId;

    public BidRequestDTO(double amount, Long bidderId, Long auctionListingId){
        this.amount = amount;
        this.timeStamp = LocalDateTime.now();
        this.bidderId = bidderId;
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
