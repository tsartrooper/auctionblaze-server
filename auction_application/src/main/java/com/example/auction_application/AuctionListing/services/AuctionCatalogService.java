package com.example.auction_application.AuctionListing.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.auction_application.AuctionListing.Status;
import com.example.auction_application.AuctionListing.dto.AuctionListingResponseDTO;
import com.example.auction_application.AuctionListing.repository.AuctionListingRepository;

@Service
public class AuctionCatalogService {

    @Autowired
    AuctionListingRepository auctionListingRepository;

    @Cacheable(value = "auctions", key = "'Auctions:'+#pageable.pageNumber+#pageable.pageSize")
    public Page<AuctionListingResponseDTO> getAuctions(Pageable pageable){
        return auctionListingRepository.findAll(pageable)
                                        .map(AuctionListingResponseDTO::new);
    }

    @Cacheable(value = "activeAuctions", key = "'Active:'+#pageable.pageNumber+#pageable.pageSize")
    public Page<AuctionListingResponseDTO> getActiveAuctions(Pageable pageable){
        return auctionListingRepository.findByAuctionStatus(Status.ACTIVE, pageable)
                                        .map(AuctionListingResponseDTO::new);
    }

    @Cacheable(value = "closedAuctions", key = "'Closed:'+#pageable.pageNumber+#pageable.pageSize")
    public Page<AuctionListingResponseDTO> getClosedAuctions(Pageable pageable){
        return auctionListingRepository.findByAuctionStatus(Status.CLOSED, pageable)
                                        .map(AuctionListingResponseDTO::new);
    }

    @Cacheable(value = "categoryAuctions", key = "#category + #pageable.pageNumber + #pageable.pageSize")
    public Page<AuctionListingResponseDTO> getAuctionsByCategory(String category, Pageable pageable){
        return auctionListingRepository.findByCategory(category, pageable)
                                        .map(AuctionListingResponseDTO::new);
    }

    @Cacheable(value = "sellerAuctions", key = "#id + #pageable.pageNumber + #pageable.pageSize")
    public Page<AuctionListingResponseDTO> getAuctionBySellerId(Long id, Pageable pageable){
        return auctionListingRepository.findBySellerId(id, pageable)
                                        .map(AuctionListingResponseDTO::new);
    }
}
