package com.example.auction_application.Authentication;

import java.io.IOException;
import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    CustomUserDetailsService userDetailsService;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException{
                System.out.println("\n\n\n entering jwt filter \n\n\n");
                final String authorizationHeader = request.getHeader("Authorization");

                String email = null;
                String jwtToken = null;
                
                if(authorizationHeader != null 
                    && authorizationHeader.startsWith("Bearer ")){
                        jwtToken = authorizationHeader.substring(7);
                        System.out.println("jwt token:"+jwtToken);
                        try{
                            email = jwtUtils.extractClaims(jwtToken).getSubject();
                        }
                catch(ExpiredJwtException e){
                    System.out.println("Jwt token has expired.");
                }
                catch(Exception e){
                    System.out.println("Unable to get JWT Token");
                }
            }

            if(email != null
                && SecurityContextHolder.getContext().getAuthentication() == null){
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

                    if(jwtUtils.validateToken(jwtToken, userDetails.getUsername())){
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
                                new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                        
                        SecurityContextHolder.getContext()
                                            .setAuthentication(usernamePasswordAuthenticationToken);
                    }
            }
            
        chain.doFilter(request, response);
    }

    
}
