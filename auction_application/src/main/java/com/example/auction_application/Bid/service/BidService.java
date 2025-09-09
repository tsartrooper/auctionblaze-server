package com.example.auction_application.Bid.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.example.auction_application.AuctionListing.Status;
import com.example.auction_application.AuctionListing.dto.AuctionListingResponseDTO;
import com.example.auction_application.AuctionListing.entity.AuctionListing;
import com.example.auction_application.AuctionListing.repository.AuctionListingRepository;
import com.example.auction_application.AuctionListing.services.AuctionWebSocketHandler;
import com.example.auction_application.Bid.BidRepository;
import com.example.auction_application.Bid.dto.BidRequestDTO;
import com.example.auction_application.Bid.entity.Bid;
import com.example.auction_application.UserModule.UserRepository;
import com.example.auction_application.UserModule.entity.WebUser;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BidService {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuctionListingRepository auctionListingRepository;

    @Autowired
    private AuctionWebSocketHandler auctionWebSocketHandler;

    @Transactional
    @CacheEvict(
        value = {"auctions", "auctionsFiltered", "activeAuctions", "closedAuctions", "categoryAuctions", "sellerAuctions", "status", "auction"},
        allEntries = true
    )
    public void createBid(BidRequestDTO bidDTO, Long bidderId) throws Exception {
        try {
            WebUser bidder = userRepository.findById(bidderId)
                    .orElseThrow(() -> new IllegalArgumentException("Bidder not found with ID: " + bidderId));

            AuctionListing auctionListing = auctionListingRepository
                    .findByIdWithLock(bidDTO.getAuctionListingId())
                    .orElseThrow(() -> new IllegalArgumentException("AuctionListing not found with ID: " + bidDTO.getAuctionListingId()));

            WebUser highestBidder = auctionListing.getCurrentHighestBidder();

            if (auctionListing.getAuctionStatus() == Status.CLOSED) {
                throw new IllegalStateException("Cannot place bid: Auction is closed");
            }
            if (auctionListing.getCurrentHighestBid() >= bidDTO.getAmount()) {
                throw new IllegalArgumentException("Bid amount must be higher than current highest bid");
            }
            if (highestBidder != null && highestBidder.getId().equals(bidderId)) {
                throw new IllegalArgumentException("Bidder is already the highest bidder");
            }

            // Update auction listing
            auctionListing.setCurrentHighestBid(bidDTO.getAmount());
            auctionListing.setCurrentHighestBidder(bidder);

            // Create bid
            Bid bid = new Bid();
            bid.setAmount(bidDTO.getAmount());
            bid.setAuctionListing(auctionListing);
            bid.setBidder(bidder);
            bid.setTimeStamp(LocalDateTime.now());

            auctionListing.addBid(bid);

            bidRepository.save(bid);
            auctionListingRepository.save(auctionListing);

            // Broadcast update
            auctionWebSocketHandler.broadcastUpdate(new AuctionListingResponseDTO(auctionListing));

        } catch (Exception e) {
            log.error("Error creating bid", e);
            throw e;  // rethrow so the caller knows
        }
    }

    public List<Bid> getAllBids() {
        return bidRepository.findAll();
    }

    public List<Bid> getBidsByBidderId(Long bidderId) {
        return bidRepository.findByBidderId(bidderId);
    }
}
