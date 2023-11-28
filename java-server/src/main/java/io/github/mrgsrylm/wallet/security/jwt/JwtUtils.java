package io.github.mrgsrylm.wallet.security.jwt;

import io.github.mrgsrylm.wallet.model.enums.TokenClaims;
import io.github.mrgsrylm.wallet.security.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import static io.github.mrgsrylm.wallet.common.Constants.*;

@Slf4j(topic = "JwtUtils")
@Component
public class JwtUtils {
    @Value("${app.security.jwtSecret}")
    private String jwtSecret;

    @Value("${app.security.jwtExpirationMs}")
    private int jwtExpirationMs;

    private Key signedInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(UserDetailsImpl userDetails, String subject) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .claim("roles", userDetails.getAuthorities())
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(signedInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return createToken(userDetails, userDetails.getUsername());
    }

    public String generateJwtToken(UserDetailsImpl customUserDetails) {
        return createToken(customUserDetails, customUserDetails.getUsername());
    }


    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signedInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsernameFromJwtToken(String token) {
        return extractClaims(token).getSubject();
    }

    public String getIDFromJwtToken(String token) {
        return extractClaims(token).get(TokenClaims.ID.getValue()).toString();
    }

    public boolean validateJwtToken(String authToken) {
        log.info("JwtUtils | validateJwtToken | authToken: {}", authToken);
        try {
            Jwts.parserBuilder().setSigningKey(signedInKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error(INVALID_JWT_TOKEN, e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error(JWT_EXPIRED, e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error(JWT_UNSUPPORTED, e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error(JWT_EMPTY, e.getMessage());
        }
        return false;
    }

    public String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
