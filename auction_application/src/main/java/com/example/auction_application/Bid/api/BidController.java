package com.example.auction_application.Bid.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.auction_application.Bid.dto.BidRequestDTO;
import com.example.auction_application.Bid.dto.BidResponseDTO;
import com.example.auction_application.Bid.service.BidService;
import com.google.api.client.http.HttpStatusCodes;
import com.example.auction_application.Authentication.JwtUtils;


import jakarta.servlet.http.HttpServletRequest;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/bids")
public class BidController {

    @Autowired
    BidService bidService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<?> createBid(@RequestBody BidRequestDTO bidDTO, HttpServletRequest request) {
        System.out.println("\n here:"+bidDTO.getAuctionListingId());
        
        String token = request.getHeader("Authorization");

        Long bidderId = jwtUtils.extractUserId(token.substring(7));

        if(bidService.createBid(bidDTO, bidderId)) return ResponseEntity.ok().build();

        return ResponseEntity.status(HttpStatusCodes.STATUS_CODE_BAD_REQUEST).body("Bid is invalid.");
    }  

    @GetMapping
    public List<BidResponseDTO> getBidsByBidderId(HttpServletRequest request) {

        String token = request.getHeader("Authorization");

        Long userId = jwtUtils.extractUserId(token.substring(7));


        return bidService.getBidsByBidderId(userId)
                        .stream()
                        .map(BidResponseDTO :: new)
                        .collect(Collectors.toList());
    }
}
