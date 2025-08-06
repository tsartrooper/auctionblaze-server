    package com.example.auction_application.AuctionListing.services;

    import java.io.IOException;
    import java.util.HashMap;
    import java.util.Map;
    import java.util.concurrent.ConcurrentHashMap;
    import java.util.concurrent.CopyOnWriteArrayList;

    import org.hibernate.jdbc.Expectations;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Component;
    import org.springframework.web.socket.CloseStatus;
    import org.springframework.web.socket.TextMessage;
    import org.springframework.web.socket.WebSocketSession;
    import org.springframework.web.socket.handler.TextWebSocketHandler;
    import org.springframework.web.util.UriComponentsBuilder;

import com.example.auction_application.AuctionListing.dto.AuctionListingResponseDTO;
import com.example.auction_application.AuctionListing.repository.AuctionListingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

    @Component
    public class AuctionWebSocketHandler extends TextWebSocketHandler {

        @Autowired
        AuctionListingRepository auctionListingRepository;

        private final Map<Long, CopyOnWriteArrayList<WebSocketSession>> auctionSessions = new ConcurrentHashMap<>();

        @Autowired
        ObjectMapper objectMapper;

        @Override
        @Transactional
        public void afterConnectionEstablished(WebSocketSession session) throws Exception{
            String uri = session.getUri().toString();
            Long auctionId = getAuctionIdFromUri(uri);
            
            auctionSessions.putIfAbsent(auctionId, new CopyOnWriteArrayList<>());       
            auctionSessions.get(auctionId).add(session);

            AuctionListingResponseDTO auctionListingResponse = auctionListingRepository.findById(auctionId).map(AuctionListingResponseDTO::new).orElse(null);

            String responseMessage = objectMapper.writeValueAsString(auctionListingResponse);

            session.sendMessage(new TextMessage(responseMessage));
        }

        private Long getAuctionIdFromUri(String uri){
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri);
            
            String auctionIdParam =  builder.build().getQueryParams().getFirst("auctionId");
            System.out.println("auctionIdParam: " + auctionIdParam);
            
            return Long.parseLong(auctionIdParam);
        }        

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
            auctionSessions.values().forEach(sessions -> sessions.remove(session));
        }

        public void broadcastUpdate(AuctionListingResponseDTO auctionUpdateDTO) throws IOException{
            String message = objectMapper.writeValueAsString(auctionUpdateDTO);

            if(auctionSessions.containsKey(auctionUpdateDTO.getId())){
                for(WebSocketSession session : auctionSessions.get(auctionUpdateDTO.getId())){
                    session.sendMessage(new TextMessage(message));
                }
            }
        }    
}
