package com.example.auction_application.Authentication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auction_application.UserModule.dto.UserRequestDTO;
import com.example.auction_application.UserModule.entity.WebUser;
import com.example.auction_application.UserModule.service.UserService;
import com.google.api.client.auth.openidconnect.IdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        try {
            String result = userService.createUser(userRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO authRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

            Optional<WebUser> optionalUser = userService.findByEmail(authRequest.getEmail());
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            WebUser user = optionalUser.get();

            if ("GOOGLE".equalsIgnoreCase(user.getAuthProvider())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("This user signed up using Google. Please use Google Login.");
            }

            String jwt = jwtUtils.generateToken(user.getUserEmail(), user.getId());

            return ResponseEntity.ok(Map.of("jwt", jwt));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed: " + ex.getMessage());
        }
    }


    @PostMapping("/google")
    public ResponseEntity<?> authenticateWithGoogle(@RequestBody Map<String, String> payload) {
        String googleToken = payload.get("token");

        System.out.println("we got the token");

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList("18033002194-3k2jhc6bk85lsahoe4ft4lq4cfto2c4r.apps.googleusercontent.com"))
                .build();

            GoogleIdToken idToken = verifier.verify(googleToken);

            if (idToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Google token");
            }

            Payload googlePayload = idToken.getPayload();

            String email = (String) googlePayload.get("email");
            String name = (String) googlePayload.get("name");
            String picture = (String) googlePayload.get("picture");

            WebUser webUser = userService.findByEmail(email).orElseGet(() -> {
                WebUser newUser = new WebUser();
                newUser.setUserEmail(email);
                newUser.setUserName(name);
                newUser.setPicture(picture);
                newUser.setRole("ROLE_USER");
                newUser.setAuthProvider("GOOGLE");
                return userService.save(newUser);
            });

            String jwt = jwtUtils.generateToken(email, webUser.getId());

            return ResponseEntity.ok(Map.of("jwt", jwt));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Google Auth Failed: " + e.getMessage());
        }
    }
}
