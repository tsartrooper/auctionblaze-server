package com.example.auction_application.AuctionListing.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.example.auction_application.AuctionListing.entity.AuctionListing;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

public class AuctionSpecifications {

    public static Specification<AuctionListing> hasCategory(String category){
        return (root, query, cb)->
                        category != null? cb.equal(root.get("category"), category) : cb.conjunction();
    }

    public static Specification<AuctionListing> priceBetween(Double minPrice, Double maxPrice){
        return (root, query, cb) ->{
                Expression<Double> price = root.get("currentHighestBid") != null? root.get("currentHighestBid") : root.get("startingPrice");  
                List<Predicate> predicates = new ArrayList<>();
                if (minPrice != null) predicates.add(cb.greaterThanOrEqualTo(price, minPrice));
                if (maxPrice != null) predicates.add(cb.lessThanOrEqualTo(price, maxPrice));
            
                return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<AuctionListing> hasStatus(String status){
        return (root, query, cb)->{
            return cb.equal(root.get("auctionStatus"), status);
        };
    }

    public static Specification<AuctionListing> hasKeyword(String keyword){
        return (root, query, cb) ->{
            List<Predicate> predicates = new ArrayList<>();
            
            predicates.add(cb.like(root.get("description"), "%"+keyword+"%"));
            predicates.add(cb.like(root.get("itemName"), "%"+keyword+"%"));

            return cb.or(predicates.toArray(new Predicate[0]));
        };
    }
    
}
