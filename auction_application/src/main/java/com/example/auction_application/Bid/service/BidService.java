package com.example.auction_application.Bid.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.example.auction_application.AuctionListing.AuctionListingRepository;
import com.example.auction_application.AuctionListing.Status;
import com.example.auction_application.AuctionListing.dto.AuctionListingResponseDTO;
import com.example.auction_application.AuctionListing.entity.AuctionListing;
import com.example.auction_application.AuctionListing.services.AuctionWebSocketHandler;
import com.example.auction_application.Bid.BidRepository;
import com.example.auction_application.Bid.dto.BidRequestDTO;
import com.example.auction_application.Bid.entity.Bid;
import com.example.auction_application.UserModule.UserRepository;
import com.example.auction_application.UserModule.entity.WebUser;

import jakarta.transaction.Transactional;

@Service
public class BidService {

    @Autowired
    BidRepository bidRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuctionListingRepository auctionListingRepository;

    @Autowired
    AuctionWebSocketHandler auctionWebSocketHandler;

    @Transactional
    @CacheEvict(value = {"auctions", "activeAuctions", "closedAuctions", "categoryAuctions", "sellerAuctions", "status", "auction"}, allEntries = true)
    public void createBid(BidRequestDTO bidDTO){
        WebUser bidder = userRepository.findById(bidDTO.getBidderId()).get();
        
        Optional<AuctionListing> auctionListing = auctionListingRepository
                                            .findById(bidDTO.getAuctionListingId());

        if(bidder == null || !auctionListing.isPresent()){
            return;
        }


        if(auctionListing.get().getAuctionStatus() == Status.CLOSED
        || auctionListing.get().getCurrentHighestBid() >= bidDTO.getAmount()) return;
        auctionListing.get().setCurrentHighestBid(bidDTO.getAmount());
        auctionListing.get().setCurrentHighestBidder(bidder);

        Bid bid = new Bid();

        bid.setAmount(bidDTO.getAmount());
        bid.setAuctionListing(auctionListing.get());
        bid.setBidder(bidder);
        bid.setTimeStamp(LocalDateTime.now());

        auctionListing.get().addBid(bid);

        bidRepository.save(bid);
        auctionListingRepository.save(auctionListing.get());

        try{
            auctionWebSocketHandler.broadcastUpdate(new AuctionListingResponseDTO(auctionListing.get()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Bid> getAllBids(){
        return bidRepository.findAll();
    }

    public List<Bid> getBidsByBidderId(Long bidderID){
        return bidRepository.findByBidderId(bidderID);
    }
    
}
