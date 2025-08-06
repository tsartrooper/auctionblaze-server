package com.example.auction_application.AuctionListing.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.auction_application.AuctionListing.Status;
import com.example.auction_application.AuctionListing.dto.AuctionListingRequestDTO;
import com.example.auction_application.AuctionListing.dto.AuctionListingResponseDTO;
import com.example.auction_application.AuctionListing.entity.AuctionListing;
import com.example.auction_application.AuctionListing.repository.AuctionListingRepository;
import com.example.auction_application.AuctionListing.scheduler.AuctionListingSchedulerService;
import com.example.auction_application.UserModule.entity.WebUser;
import com.example.auction_application.UserModule.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class AuctionListingService {
    @Autowired 
    AuctionListingRepository auctionListingRepository;

    @Autowired
    UserService userService;

    @Autowired
    AuctionListingSchedulerService auctionListingSchedulerService;

    @Autowired
    AuctionWebSocketHandler auctionWebSocketHandler;

    @Transactional
    @CacheEvict(value = {"auctions", "activeAuctions", "closedAuctions", "categoryAuctions", "sellerAuctions", "status", "auction"}, allEntries = true) 
    public boolean createAuctionListing(AuctionListingRequestDTO auctionListingDTO, Long sellerId) throws IOException{
        System.out.println("\n user id: " + sellerId);
        WebUser seller = userService.findById(sellerId);

        if(seller == null) return false;

        AuctionListing auctionListing = new AuctionListing();

        auctionListing.setStartingPrice(auctionListingDTO.getStartingPrice());
        auctionListing.setDescription(auctionListingDTO.getDescription());
        auctionListing.setItemName(auctionListingDTO.getItemName());
        auctionListing.setSeller(seller);
        auctionListing.setStartTime(auctionListingDTO.getStartTime());
        auctionListing.setAuctionStatus(Status.SCHEDULED);
        auctionListing.setCurrentHighestBid(auctionListingDTO.getStartingPrice());
        auctionListing.setEndTime(auctionListingDTO.getEndTime());
        auctionListing.setCategory(auctionListingDTO.getCategory());
        auctionListing.setPicture(auctionListingDTO.getPicture());

        auctionListingRepository.save(auctionListing);
        seller.getSellingAuctions().add(auctionListing);
        userService.save(seller);
        auctionListingSchedulerService.scheduleAuctionActivation(
                                                auctionListing.getId(),
                                                auctionListing.getStartTime());
        auctionListingSchedulerService.scheduleAuctionClosing(
                                                auctionListing.getId(),
                                                auctionListing.getEndTime());
        
        try{
            auctionWebSocketHandler.broadcastUpdate(new AuctionListingResponseDTO(auctionListing));
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return true;
    }

    @Transactional
    @CacheEvict(value = {"auctions", "activeAuctions", "closedAuctions", "categoryAuctions", "sellerAuctions", "status", "auction"}, allEntries = true) 
    public void save(AuctionListing auctionListing){
        auctionListingRepository.save(auctionListing);
    }

    @Cacheable(value = "auctions", key = "'auctions'")
    public Page<AuctionListingResponseDTO> getAuctionListings(Pageable pageable){
        return auctionListingRepository.findAll(pageable)
                                        .map(AuctionListingResponseDTO::new);
    }

    @Cacheable(value = "auction", key = "'auction_id:'+#id")
    public AuctionListing getAuctionListingById(Long id){
        Optional<AuctionListing> auctionListing = auctionListingRepository.findById(id);
        if(!auctionListing.isPresent()) return null;
        
        return auctionListing.get();
    }    

    @Cacheable(value = "status", key = "#status + #pageable.pageNumber+#pageable.pageSize")
    public Page<AuctionListing> getByAuctionStatus(Status status, Pageable pageable){
        return auctionListingRepository.findByAuctionStatus(status, pageable);
    }

    @Transactional
    @CacheEvict(value = {"auctions", "activeAuctions", "closedAuctions", "categoryAuctions", "sellerAuctions", "status", "auction"}, allEntries = true) 
    public void deleteAllAuctions(){
        auctionListingRepository.deleteAll();
        
        return;
    }

    @Transactional
    @CacheEvict(value = {"auctions", "activeAuctions", "closedAuctions", "categoryAuctions", "sellerAuctions", "status", "auction"}, allEntries = true) 
    public void closeAuction(Long auctionId){
        Optional<AuctionListing> auctionListing = auctionListingRepository.findById(auctionId);
        
        if(!auctionListing.isPresent()) return;
        
        auctionListing.get().setAuctionStatus(Status.CLOSED);

        auctionListingRepository.save(auctionListing.get());

        try{
            auctionWebSocketHandler.broadcastUpdate(new AuctionListingResponseDTO(auctionListing.get()));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return;
    }

    @Transactional
    @CacheEvict(value = {"auctions", "activeAuctions", "closedAuctions", "categoryAuctions", "sellerAuctions", "status", "auction"}, allEntries = true) 
    public void activateAuction(Long auctionId) {
        Optional<AuctionListing> auctionListing = auctionListingRepository.findById(auctionId);
        
        if(!auctionListing.isPresent()) return;

        if(!auctionListing.get().getAuctionStatus().equals(Status.CLOSED)){
            auctionListing.get().setAuctionStatus(Status.ACTIVE);
            System.out.println("activating inside service");
        }

        auctionListingRepository.save(auctionListing.get());
        try{
            auctionWebSocketHandler.broadcastUpdate(new AuctionListingResponseDTO(auctionListing.get()));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return;
    }

    @Cacheable(value = "auction", key="'auction: '+ #id")
    public AuctionListingResponseDTO getAuctionDTOById(Long id){
        Optional<AuctionListing> auctionListing = auctionListingRepository.findById(id);
        if(!auctionListing.isPresent()) return null;
        
        return new AuctionListingResponseDTO(auctionListing.get());
    }

}
