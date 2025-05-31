package com.example.auction_application.AuctionListing.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.auction_application.AuctionListing.services.AuctionListingService;

public class AuctionActivationJob implements Job{
    @Autowired
    AuctionListingService auctionListingService;

    @Override
    public void execute(JobExecutionContext context){
        Long auctionId = (Long) context.getJobDetail().getJobDataMap().get("auctionActivationId");
        auctionListingService.activateAuction(auctionId);
    }   
    
}
