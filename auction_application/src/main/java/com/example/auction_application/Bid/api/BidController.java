package com.example.auction_application.Bid.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auction_application.Bid.dto.BidRequestDTO;
import com.example.auction_application.Bid.dto.BidResponseDTO;
import com.example.auction_application.Bid.service.BidService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/bids")
public class BidController {

    @Autowired
    BidService bidService;

    @PostMapping
    public void createBid(@RequestBody BidRequestDTO bidDTO) {
        System.out.println("\n here:"+bidDTO.getAuctionListingId());
        bidService.createBid(bidDTO);

        return;
    }

    @GetMapping
    public List<BidResponseDTO> getAllBids() {
        System.out.println("here? ");
        return bidService.getAllBids()
                         .stream()
                         .map(BidResponseDTO :: new)
                         .collect(Collectors.toList());
    }    

    @GetMapping("/{bidder_id}")
    public List<BidResponseDTO> getBidsByBidderId(@PathVariable(name="bidder_id") Long bidder_id) {
        return bidService.getBidsByBidderId(bidder_id)
                         .stream()
                         .map(BidResponseDTO :: new)
                         .collect(Collectors.toList());
    }
}
