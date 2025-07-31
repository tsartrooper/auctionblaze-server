package com.example.auction_application.Authentication.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.auction_application.Authentication.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    private final SecurityConfigProperties securityConfigProperties;

    public SpringSecurityConfiguration(SecurityConfigProperties securityConfigProperties){
        this.securityConfigProperties = securityConfigProperties;
    }


    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration)
        throws Exception{
            return authenticationConfiguration.getAuthenticationManager();        
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.cors()
        .and()
        .csrf(csrf -> {
                    if(!securityConfigProperties.isCsrfEnabled()){
                        csrf.disable();
                    }
                })
                .authorizeRequests(requests -> requests
                        .requestMatchers(
                            securityConfigProperties.getPublicPaths().toArray(new String[0])).permitAll()
                        .requestMatchers(
                            securityConfigProperties.getAdminPaths().toArray(new String[0])).hasAnyRole(
                                securityConfigProperties.getAdminRoles().toArray(new String[0]))
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }
    
}
