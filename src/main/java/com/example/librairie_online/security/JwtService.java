package com.example.librairie_online.security;


import com.example.librairie_online.entity.Client;
import com.example.librairie_online.service.ClientService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;


@Service
public class JwtService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JwtService.class);
    private ClientService clientService;

    @Value("${JWT_SECRET}")
    private String encryptionKey ;

    public JwtService(ClientService clientService) {
        this.clientService = clientService;
    }



    public Map<String,String> generate (String email){
        Client client = (Client) this.clientService.loadUserByUsername(email);
        return this.generateJwt(client);
    }

    private Map<String, String> generateJwt(Client client) {

        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 30 * 1000 * 60;
        final Map<String, String> claims = Map.of("nom", client.getNom(),
                "prenom", client.getPrenom(),
                "email", client.getEmail(),
                "role", client.getRole().getRole().toString()
        );
        final String bearer = Jwts.builder()
                .issuedAt(new Date(currentTime))
                .expiration(new Date(expirationTime))
                .subject(client.getEmail())
                .claim("claims", claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();


        return Map.of("bearer", bearer);
    }

    private Key getKey() {
        final byte[] decode = Decoders.BASE64.decode(encryptionKey);
        return Keys.hmacShaKeyFor(decode);
    }

    public boolean isTokenExpired(String token) {
        return true;
    }

    public String extractUsername(String token) {
        return null;
    }
}
