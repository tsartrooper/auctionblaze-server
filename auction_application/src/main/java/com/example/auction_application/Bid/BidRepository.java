package com.example.auction_application.Bid;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auction_application.Bid.entity.Bid;

public interface BidRepository extends JpaRepository<Bid, Long> {
    public List<Bid> findByBidderId(Long bidderId);    
}
