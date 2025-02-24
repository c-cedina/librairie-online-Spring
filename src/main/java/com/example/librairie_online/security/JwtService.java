package com.example.librairie_online.security;


import com.example.librairie_online.entity.Client;
import com.example.librairie_online.entity.Jwt;
import com.example.librairie_online.repository.JwtRepository;
import com.example.librairie_online.service.ClientService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;

import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.*;
import java.util.function.Function;


@Service
public class JwtService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JwtService.class);
    public static final String BEARER = "bearer";
    private final ClientService clientService;
    private JwtRepository jwtRepository;

    @Value("${JWT_SECRET}")
    private String encryptionKey ;

    public JwtService(ClientService clientService, JwtRepository jwtRepository) {
        this.clientService = clientService;
        this.jwtRepository = jwtRepository;
    }



    public Map<String,String> generate (String email){
        Client client = (Client) this.clientService.loadUserByUsername(email);
        List<Jwt> jwtList = this.jwtRepository.findAllByEmail(email);

        if (!jwtList.isEmpty()){
        jwtList.forEach(jwt -> jwt.setDesactive(true));
        this.jwtRepository.saveAll(jwtList);
        }
        final Map<String, String> jwtMap = this.generateJwt(client);
        final Jwt jwt = Jwt
                .builder()
                .token(jwtMap.get(BEARER))
                .desactive(false)
                .expire(false)
                .client(client)
                .build();
        this.jwtRepository.save(jwt);
        return jwtMap;
    }

    private Map<String, String> generateJwt(Client client) {

        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 30 * 1000 * 60;

        final Map<String, Object> claims = Map.of("nom", client.getNom(),
                "prenom", client.getPrenom(),
                "role", client.getRole().getRole().toString(),
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT, client.getEmail()
        );
        final String bearer = Jwts.builder()
                .issuedAt(new Date(currentTime))
                .expiration(new Date(expirationTime))
                .subject(client.getEmail())
                .claim("claims", claims)
                .signWith(getKey())
                .compact();


        return Map.of(BEARER, bearer);
    }

    private SecretKey getKey() {
        final byte[] decode = Decoders.BASE64.decode(encryptionKey);
        return Keys.hmacShaKeyFor(decode);
    }

    public boolean isTokenExpired(String token) {
        Date expiration = this.getClaim(token, Claims::getExpiration);;
        return expiration.before(new Date());

    }

    private <T> T getClaim(String token, Function<Claims,T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
    }

    public String extractUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public Jwt readByToken(String token) {
        return this.jwtRepository.findByToken(token).orElseThrow(()-> new RuntimeException("Token not found"));
    }

    public void deconnect() {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Jwt> jwtList = this.jwtRepository.findAllByEmail(client.getEmail());
        if (!jwtList.isEmpty()){
        jwtList.forEach(jwt -> jwt.setDesactive(true));
        this.jwtRepository.saveAll(jwtList);
        }else {
            throw new RuntimeException("No token found with this user "+client.getEmail());
        }

    }
}
