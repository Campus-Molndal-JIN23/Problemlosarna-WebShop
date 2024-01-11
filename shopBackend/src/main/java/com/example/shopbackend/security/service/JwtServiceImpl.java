package com.example.shopbackend.security.service;


import com.example.shopbackend.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
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
    public String generateToken(User userDetails) {
        Long userId = userDetails.getId();
        String userName = userDetails.getUsername();
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = userDetails.getRoles().stream()
                .map(role -> role.getAuthority().name())
               .collect(Collectors.toList());
        claims.put("roles", roles);
        claims.put("userID", userId);
        claims.put("username",userName);


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
        Instant now = Instant.now();
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(3, ChronoUnit.HOURS)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    @Override
    public Set<String> extractRoles(String token) {
        return extractClaim(token, claims -> new HashSet<>((List<String>) claims.get("roles")));
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