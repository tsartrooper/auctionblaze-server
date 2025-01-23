package com.example.auction_application.AuctionListing;

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

import com.example.auction_application.UserModule.UserService;
import com.example.auction_application.UserModule.WebUser;

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
    public void createAuctionListing(AuctionListingRequestDTO auctionListingDTO, Long sellerId) throws IOException{
        System.out.println("\n user id: " + sellerId);
        WebUser seller = userService.findById(sellerId);

        if(seller == null) return;

        AuctionListing auctionListing = new AuctionListing();

        auctionListing.setStartingPrice(auctionListingDTO.getStartingPrice());
        auctionListing.setDescription(auctionListingDTO.getDescription());
        auctionListing.setItemName(auctionListingDTO.getItemName());
        auctionListing.setDuration(auctionListingDTO.getDuration());
        auctionListing.setSeller(seller);
        auctionListing.setStartTime(LocalDateTime.now().plusMinutes(auctionListingDTO.getStartDelay()));
        auctionListing.setAuctionStatus(Status.SCHEDULED);
        auctionListing.setCurrentHighestBid(auctionListingDTO.getStartingPrice());
        auctionListing.setEndTime(LocalDateTime.now().plusMinutes(auctionListing.getDuration()+auctionListingDTO.getStartDelay()));
        auctionListing.setCategory(auctionListingDTO.getCategory());

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

        return;
    }

    @Transactional
    @CacheEvict(value = {"auctions", "activeAuctions", "closedAuctions", "categoryAuctions", "sellerAuctions", "status", "auction"}, allEntries = true) 
    public void save(AuctionListing auctionListing){
        auctionListingRepository.save(auctionListing);
    }

    @Cacheable(value = "auctions", key = "'auctions:'+#pageable.pageNumber+#pageable.pageSize")
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
        
        return;
    }

    @Transactional
    @CacheEvict(value = {"auctions", "activeAuctions", "closedAuctions", "categoryAuctions", "sellerAuctions", "status", "auction"}, allEntries = true) 
    public void activateAuction(Long auctionId) {
        Optional<AuctionListing> auctionListing = auctionListingRepository.findById(auctionId);
        
        if(!auctionListing.isPresent()) return;
        
        auctionListing.get().setAuctionStatus(Status.ACTIVE);

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
