package com.educo.educo.security;

import com.educo.educo.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.educo.educo.security.SecurityConstants.JWT_SECRET;
import static com.educo.educo.security.SecurityConstants.VALID_DURATION;

@Component
public class JwtTokenProvider {
    public String generateToken(User user) {
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + VALID_DURATION);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("fullName", user.getFullName());

        return Jwts.builder()
                .setSubject(user.getId())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }
}
