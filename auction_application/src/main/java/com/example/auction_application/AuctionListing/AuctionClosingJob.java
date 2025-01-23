package com.example.auction_application.AuctionListing;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

public class AuctionClosingJob implements Job{

    @Autowired
    AuctionListingService auctionListingService;

    @Override
    public void execute(JobExecutionContext context){
        Long auctionId = (Long) context.getJobDetail().getJobDataMap().get("auctionCloseId");
        auctionListingService.closeAuction(auctionId);
    }
    
}
