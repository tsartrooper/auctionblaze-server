package com.example.auction_application.AuctionListing.api;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.query.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auction_application.AuctionListing.dto.AuctionListingResponseDTO;
import com.example.auction_application.AuctionListing.services.AuctionCatalogService;


@RestController
@RequestMapping("/catalog")
public class AuctionCatalogController {

    @Autowired
    AuctionCatalogService auctionCatalogService;

    @GetMapping("/all")
    public Page<AuctionListingResponseDTO> getAllAuctions(
        @PageableDefault(page = 0, size = 20, sort = "id") Pageable pageable) {
        return auctionCatalogService.getAuctions(pageable);
    }

    @GetMapping("/active")
    public Page<AuctionListingResponseDTO> getActiveAuctions(Pageable pageable) {
        return auctionCatalogService.getActiveAuctions(pageable);
    }

    @GetMapping("/category/{category}")
    public Page<AuctionListingResponseDTO> getAuctionsByCategory(
                                @PathVariable(name="category") String category, Pageable pageable) {
        return auctionCatalogService.getAuctionsByCategory(category, pageable);
    }
    
    @GetMapping("/seller/{seller_id}")
    public Page<AuctionListingResponseDTO> getAuctionsBySellerId(@PathVariable(name="seller_id") long sellerId,
                                                                    Pageable pageable) {
        return auctionCatalogService.getAuctionBySellerId(sellerId, pageable);
    }   
}
