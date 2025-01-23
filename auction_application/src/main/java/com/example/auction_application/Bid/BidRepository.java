package com.example.auction_application.Bid;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {
    public List<Bid> findByBidderId(Long bidderId);    
}
