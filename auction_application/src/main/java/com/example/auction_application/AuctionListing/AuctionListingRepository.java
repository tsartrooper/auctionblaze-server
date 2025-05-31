package com.example.auction_application.AuctionListing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auction_application.AuctionListing.entity.AuctionListing;

import jakarta.transaction.Transactional;

import java.util.List;


public interface AuctionListingRepository extends JpaRepository<AuctionListing,Long> {
    Page<AuctionListing> findAll(Pageable pageable);
    Page<AuctionListing> findByAuctionStatus(Pageable pageable, Status auctionStatus);
    Page<AuctionListing> findByCategory(String category, Pageable pageable);
    Page<AuctionListing> findBySellerId(Long sellerId, Pageable pageable);
    Page<AuctionListing> findByAuctionStatus(Status auctionStatus, Pageable pageable);
    List<AuctionListing> findByCategory(String category);
    List<AuctionListing> findBySellerId(Long sellerId);
    AuctionListing findById(long id);

}