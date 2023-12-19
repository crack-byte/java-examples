package com.crackbyte.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;



public class JwtProcessor {

    private String secretKey;
    private long expiration;

    public static void main(String[] args) {
        JwtProcessor jwtProcessor = new JwtProcessor();
        User.UserBuilder userBuilder = User.builder()
            .username("John Doe")
            .authorities("ADMIN", "USER")
            .password("password")
            .accountExpired(false) // Set true if the account is expired
            .accountLocked(false) // Set true if the account is locked
            .credentialsExpired(false);
        String token = jwtProcessor.generateToken(userBuilder.build());
        System.out.println(token);
        Claims allClaimsFromToken = jwtProcessor.getAllClaimsFromToken(token);
        System.out.println(allClaimsFromToken);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey("secretKeysecretKeysecretKeysecretKeysecretKey").build().parseClaimsJws(token).getBody();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        try {
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails) {

        String secretKey = "secretKeysecretKeysecretKeysecretKeysecretKey";
        long currentTime = System.currentTimeMillis();
        long expiryTime = currentTime + 1000 * 60 * 60; // Set expiration time in milliseconds (1 hour)
        Map<String, Object> map = new HashMap<>();
        map.put("authorities", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray());
        JwtBuilder builder = Jwts.builder()
            .setSubject("John Doe")
            .setClaims(map)
            .setExpiration(new Date(expiryTime))
            .signWith(SignatureAlgorithm.HS256, secretKey);
        return builder.compact();
    }
}
