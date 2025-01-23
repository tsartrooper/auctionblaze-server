package com.example.auction_application.AuctionListing;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.hibernate.annotations.Cache;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class AuctionListingSchedulerService {
    
    @Autowired
    Scheduler scheduler;

    @CacheEvict(value = {"auctions", "activeAuctions", "closedAuctions", "categoryAuctions", "sellerAuctions", "status", "auction"}, allEntries = true) 
    public void scheduleAuctionClosing(Long auctionId, LocalDateTime endTime){
        JobDetail jobDetail = JobBuilder.newJob(AuctionClosingJob.class)
                                            .withIdentity("auctionClosingJob-"+auctionId)
                                            .build();

        jobDetail.getJobDataMap().put("auctionCloseId", auctionId);

        Trigger trigger = TriggerBuilder.newTrigger()
                                        .withIdentity("auctionClosingTrigger-"+auctionId)
                                        .startAt(Timestamp.valueOf(endTime))
                                        .build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @CacheEvict(value = {"auctions", "activeAuctions", "closedAuctions", "categoryAuctions", "sellerAuctions", "status", "auction"}, allEntries = true)
    public void scheduleAuctionActivation(Long auctionId, LocalDateTime startTime){
        JobDetail jobDetail = JobBuilder.newJob(AuctionActivationJob.class)
                                            .withIdentity("auctionStartJob-"+auctionId)
                                            .build();

        jobDetail.getJobDataMap().put("auctionActivationId", auctionId);

        Trigger trigger = TriggerBuilder.newTrigger()
                                        .withIdentity("auctionActivationTrigger-"+auctionId)
                                        .startAt(Timestamp.valueOf(startTime))
                                        .build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();}
    }
}
