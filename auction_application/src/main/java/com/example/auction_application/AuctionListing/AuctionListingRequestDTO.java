package com.example.auction_application.AuctionListing;

public class AuctionListingRequestDTO {
    private double startingPrice;
    private String description;
    private String itemName;
    private long duration;
    private long startDelay;
    private String category;

    public AuctionListingRequestDTO(double startingPrice, String description, String itemName, long duration, String category, long startDelay) {
        this.startingPrice = startingPrice;
        this.description = description;
        this.itemName = itemName;
        this.duration = duration;
        this.category = category;
        this.startDelay = startDelay;
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
    public long getDuration() {
        return duration;
    }
    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getStartDelay() {
        return startDelay;
    }

    public void setStartDelay(long startDelay) {
        this.startDelay = startDelay;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
