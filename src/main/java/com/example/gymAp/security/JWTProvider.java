package com.example.gymAp.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class JWTProvider {

    @Value("${app.jwt.access.key}")
    private String ACCESS_SECRET;

    @Value("${app.jwt.access.expiration}")
    private long ACCESS_EXPIRATION;
    private final Set<String> invalidatedTokens = new HashSet<>();

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roleList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        claims.put("roles", roleList);

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + ACCESS_EXPIRATION);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, ACCESS_SECRET)
                .compact();
    }

    public String getUsername(String token){
        log.info("Get userName from token:{}", token);
        return  getAllClaimsFromToken(token).getSubject();
    }

    public List<String > getRoles(String token){
        log.info("Get roles from token:{}", token);
        return getAllClaimsFromToken(token).get("roles", List.class);
    }
    public Claims getAllClaimsFromToken(String token){
        return Jwts.parser()
                .setSigningKey(ACCESS_SECRET)
                .build()
                .parseSignedClaims(token).getBody();
    }


    public void invalidateToken(String token) {
        invalidatedTokens.add(token);
        log.info("Token invalidated: '{}'", token);

    }



}

