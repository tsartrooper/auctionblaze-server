package com.example.auction_application.AuctionListing.dto;

import java.time.Instant;

public class AuctionListingRequestDTO {
    private double startingPrice;
    private String description;
    private String itemName;
    private String category;
    private Instant startTime;
    private Instant endTime;
    private String picture;

    public AuctionListingRequestDTO(double startingPrice, String description, String itemName, String category, String endTime, String startTime, String picture) {

        System.out.println("start time: "+startTime+" Instant start time: "+Instant.parse(startTime));
        this.startingPrice = startingPrice;
        this.description = description;
        this.itemName = itemName;
        this.category = category;
        this.startTime = Instant.parse(startTime);
        this.endTime = Instant.parse(endTime);
        this.picture = picture;
    }

    public AuctionListingRequestDTO() {
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

    public Instant getEndTime(){
        return this.endTime;
    }
    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString(){
        return "AuctionListingRequestDTO{" +
                "startingPrice=" + startingPrice +
                ", description='" + description + '\'' +
                ", itemName='" + itemName + '\'' +
                ", category='" + category + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

}
