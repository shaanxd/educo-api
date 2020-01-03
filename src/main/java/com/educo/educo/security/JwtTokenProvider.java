package com.educo.educo.security;

import com.educo.educo.entities.User;
import io.jsonwebtoken.*;
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

    boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT Signature");
        } catch (MalformedJwtException ex) {
            System.out.println("JWT Token is altered.");
        } catch (ExpiredJwtException ex) {
            System.out.println("Token has expired.");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT Token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }

    String extractUserFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();

        return (String) claims.get("id");
    }
}
