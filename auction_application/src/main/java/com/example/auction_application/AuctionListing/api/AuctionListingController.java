package com.example.auction_application.AuctionListing.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.auction_application.AuctionListing.dto.AuctionListingRequestDTO;
import com.example.auction_application.AuctionListing.dto.AuctionListingResponseDTO;
import com.example.auction_application.AuctionListing.services.AuctionListingService;
import com.example.auction_application.Authentication.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/auctions")
public class AuctionListingController {
    
    @Autowired
    AuctionListingService auctionListingService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<?> createAuctionListing(@RequestBody AuctionListingRequestDTO auctionListingDTO,
                                                    HttpServletRequest request) throws Exception{
        System.out.println("\n Creating Auction Listing");
        
        String token = request.getHeader("Authorization");

        Long sellerId = jwtUtils.extractUserId(token.substring(7));

        auctionListingService.createAuctionListing(auctionListingDTO, sellerId);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Page<AuctionListingResponseDTO> getAllAuctions(Pageable pageable){
        return auctionListingService.getAuctionListings(pageable);
    }

    @DeleteMapping
    public void deleteAllAuctions(){
        auctionListingService.deleteAllAuctions();
        return;
    }    

    @GetMapping("/auction")
    public AuctionListingResponseDTO searchAuctionById(@RequestParam(name="auctionId") Long id){
        return auctionListingService.getAuctionDTOById(id);
    }

}
