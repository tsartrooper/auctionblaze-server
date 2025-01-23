package com.example.auction_application.Authentication.Config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityConfigProperties {
    private boolean csrfEnabled;
    private List<String> publicPaths;
    private List<String> adminPaths;
    private List<String> adminRoles;

    public boolean isCsrfEnabled() {
        return csrfEnabled;
    }
    public void setCsrfEnabled(boolean csrfEnabled) {
        this.csrfEnabled = csrfEnabled;
    }
    public List<String> getPublicPaths() {
        return publicPaths;
    }
    public void setPublicPaths(List<String> publicPaths) {
        this.publicPaths = publicPaths;
    }
    public List<String> getAdminPaths() {
        return adminPaths;
    }
    public void setAdminPaths(List<String> adminPaths) {
        this.adminPaths = adminPaths;
    }
    public List<String> getAdminRoles() {
        return adminRoles;
    }
    public void setAdminRoles(List<String> adminRoles) {
        this.adminRoles = adminRoles;
    }


    
    
}
