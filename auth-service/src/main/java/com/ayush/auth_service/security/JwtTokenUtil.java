package com.ayush.auth_service.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secret; // Ensure this is at least 32 chars long in application.properties

    @Value("${jwt.expiration}")
    private Long expiration;

    // Generate token for user (uses the same key as verification)
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(getSignKey()) // Uses the same key for signing
                .compact();
    }

    // Use the injected `secret` (not a random key)
    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret); // If secret is Base64-encoded
        // OR, if secret is raw:
        // byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes); // Ensures 256-bit key
    }

    // Extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract expiration date from token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey()) // Consistent key usage
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Validate token (structure + signature + expiry)
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validate token structure + signature
    public boolean validateTokenStructure(String token) {
        try {
            Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (JwtException ex) {
            System.err.println("JWT validation failed: " + ex.getMessage());
            return false;
        }
    }
}