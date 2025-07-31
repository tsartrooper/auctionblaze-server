package com.example.auction_application.AuctionListing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.auction_application.AuctionListing.entity.AuctionListing;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;


public interface AuctionListingRepository extends JpaRepository<AuctionListing,Long> {
    Page<AuctionListing> findAll(Pageable pageable);
    Page<AuctionListing> findByCategory(String category, Pageable pageable);
    Page<AuctionListing> findBySellerId(Long sellerId, Pageable pageable);
    Page<AuctionListing> findByAuctionStatus(Status auctionStatus, Pageable pageable);
    List<AuctionListing> findByCategory(String category);
    List<AuctionListing> findBySellerId(Long sellerId);
    
    @Transactional
    AuctionListing findById(long id);

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM AuctionListing a WHERE a.id = :id")
    Optional<AuctionListing> findByIdWithLock(@Param("id") Long id);

}