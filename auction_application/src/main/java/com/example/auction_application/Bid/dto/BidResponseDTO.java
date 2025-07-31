package com.example.auction_application.Bid.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.example.auction_application.Bid.entity.Bid;

public class BidResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private double amount;
    private LocalDateTime timeStamp;
    private String bidderName;
    private Long auctionListingId;
    private String auctionListingName;
    private String bidderEmail;

    public BidResponseDTO(Bid bid){
        this.id = bid.getId();
        this.amount = bid.getAmount();
        this.auctionListingId = bid.getAuctionListing().getId();
        this.auctionListingName = bid.getAuctionListing().getItemName();
        this.bidderName = bid.getBidder().getUserName();
        this.timeStamp = bid.getTimeStamp(); 
        this.bidderEmail = bid.getBidder().getUserEmail();  
        
        System.out.println("bid timestamp: "+this.getTimeStamp());
    }

    public BidResponseDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuctionListingName() {
        return auctionListingName;
    }

    public void setAuctionListingName(String auctionListingName) {
        this.auctionListingName = auctionListingName;
    }

    public String getBidderEmail() {    
        return bidderEmail;
    }

    public void setBidderEmail(String bidderEmail) {
        this.bidderEmail = bidderEmail;
    }

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

    public String getBidderName() {
        return bidderName;
    }

    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }

    public Long getAuctionListingId() {
        return auctionListingId;
    }

    public void setAuctionListingId(Long auctionListingId) {
        this.auctionListingId = auctionListingId;
    }
}
