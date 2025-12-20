package com.example.auction_application.Authentication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
                final String authorizationHeader = request.getHeader("Authorization");

                String email = null;
                String jwtToken = null;
                
                if(authorizationHeader != null 
                    && authorizationHeader.startsWith("Bearer ")){
                        jwtToken = authorizationHeader.substring(7);
                        try{
                            email = jwtUtils.extractClaims(jwtToken).getSubject();
                        }
                catch(ExpiredJwtException e){
                    // Jwt token has expired
                }
                catch(Exception e){
                    // Unable to get JWT Token
                }
            }

            if(email != null
                && SecurityContextHolder.getContext().getAuthentication() == null){
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

                    if(jwtUtils.validateToken(jwtToken, userDetails.getUsername())){
                        // Extract roles from token claims
                        List<String> roles = jwtUtils.extractRoles(jwtToken);
                        List<GrantedAuthority> authorities = new ArrayList<>();
                        
                        if(roles != null) {
                            for(String role : roles) {
                                authorities.add(new SimpleGrantedAuthority(role));
                            }
                        } else {
                            // Fallback to user details authorities if roles not in token
                            authorities.addAll(userDetails.getAuthorities());
                        }
                        
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
                                new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    authorities);
                        usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                        
                        SecurityContextHolder.getContext()
                                            .setAuthentication(usernamePasswordAuthenticationToken);
                    }
            }
            
        chain.doFilter(request, response);
    }
}
