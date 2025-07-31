package com.example.auction_application.AuctionListing.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.auction_application.AuctionListing.Status;
import com.example.auction_application.AuctionListing.entity.AuctionListing;
import com.example.auction_application.Bid.dto.BidResponseDTO;

public class AuctionListingResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private double startingPrice;
    private String description;
    private String itemName;
    private Long sellerId;
    private List<BidResponseDTO> bids;
    private long currentHighestBidderId;
    private double currentHighestBid;
    private LocalDateTime endTime;
    private LocalDateTime startTime;
    private Status status;
    private String category;
    private String picture;

    public AuctionListingResponseDTO(AuctionListing auctionListing){
        this.id = auctionListing.getId();
        this.startingPrice = auctionListing.getStartingPrice();
        this.description = auctionListing.getDescription();
        this.itemName = auctionListing.getItemName();
        this.sellerId = auctionListing.getSeller().getId();
        this.picture = auctionListing.getPicture();
        this.endTime = auctionListing.getEndTime();
        this.startTime = auctionListing.getStartTime();
        this.category = auctionListing.getCategory();
        if(auctionListing.getCurrentHighestBidder() != null){
            this.currentHighestBidderId = auctionListing.getCurrentHighestBidder().getId();
        }
        this.currentHighestBid = auctionListing.getCurrentHighestBid();
        this.status = auctionListing.getAuctionStatus();
        this.bids = auctionListing.getBids()
                                  .stream()
                                  .map(BidResponseDTO :: new)
                                  .collect(Collectors.toList());
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<BidResponseDTO> getBids() {
        return bids;
    }

    public void setBids(List<BidResponseDTO> bids) {
        this.bids = bids;
    }

    public long getCurrentHighestBidderId() {
        return currentHighestBidderId;
    }

    public void setCurrentHighestBidderId(long currentHighestBidderId) {
        this.currentHighestBidderId = currentHighestBidderId;
    }

    public double getCurrentHighestBid() {
        return currentHighestBid;
    }

    public void setCurrentHighestBid(double currentHighestBid) {
        this.currentHighestBid = currentHighestBid;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
