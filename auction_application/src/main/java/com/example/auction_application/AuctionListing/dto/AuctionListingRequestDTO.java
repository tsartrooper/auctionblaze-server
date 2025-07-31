package com.example.auction_application.AuctionListing.dto;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.cglib.core.Local;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AuctionListingRequestDTO {
    private double startingPrice;
    private String description;
    private String itemName;
    private String category;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String picture;

    public AuctionListingRequestDTO(double startingPrice, String description, String itemName, String category, LocalDateTime endTime, LocalDateTime startTime, String picture) {
        this.startingPrice = startingPrice;
        this.description = description;
        this.itemName = itemName;
        this.category = category;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public LocalDateTime getEndTime(){
        return this.endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setStartTime(LocalDateTime startTime) {
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
