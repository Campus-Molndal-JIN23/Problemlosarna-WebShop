package com.example.shopbackend.security.service;

import com.example.shopbackend.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {

    private String jwtSigningKey = "0b3a2839ae978330c29d866068171086524b7cd5a7e8a7ddc5fd7a893f7caf7b";

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        Map<String, Object> claims = new HashMap<>();
        Set<String> roles = userDetails.getRoles().stream()
                .map(role -> role.getAuthority().name())
                .collect(Collectors.toSet());
        claims.put("roles", roles);
        claims.put("userName", userId);

        return generateToken(claims, userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60L * 60L))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    @Override
    public Set<String> extractRoles(String token) {
        return extractClaim(token, claims -> (Set<String>) claims.get("roles"));
    }

    @Override
    public Long extractId(String token) {
        return extractClaim(token, claims -> (Number) claims.get("userName")).longValue();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}