package com.example.gymAp.security;

import com.example.gymAp.dao.UserDAO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JWTProvider {

    @Value("${app.jwt.access.key}")
    private String ACCESS_SECRET;

    @Value("${app.jwt.access.expiration}")
    private long ACCESS_EXPIRATION;

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
        return  getAllClaimsFromToken(token).getSubject();
    }

    public List<String > getRoles(String token){
        return getAllClaimsFromToken(token).get("roles", List.class);
    }
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser()
                .setSigningKey(ACCESS_SECRET)
                .build()
                .parseSignedClaims(token).getBody();
    }

}
