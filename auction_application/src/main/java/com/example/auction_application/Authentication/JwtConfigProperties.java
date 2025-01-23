package com.example.auction_application.Authentication;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="jwt")
public class JwtConfigProperties {

    private String secret;
    private Long expirationLimit;
    
    public String getSecret() {
        return secret;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }
    public Long getExpirationLimit() {
        return expirationLimit;
    }
    public void setExpirationLimit(Long expirationLimit) {
        this.expirationLimit = expirationLimit;
    }
}
