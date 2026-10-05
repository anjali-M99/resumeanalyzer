package com.resumeanalyzer.resumeanalyzer.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.resumeanalyzer.resumeanalyzer.config.CommonProperties;
import com.resumeanalyzer.resumeanalyzer.constants.Constants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;

@Component
public class JWTHelper {
	
	private static final String SECRET_KEY = "u8fX7zQ2k9Yh3vLw5aN0rB1cD4eF7gH2jK9mP6qR8tU1vW3xY5zA7bC9dE2fG4hI";
	private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }

}
