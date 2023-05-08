package com.poywallet.poywalletbackend.security.jwt;

import com.poywallet.poywalletbackend.security.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.poywallet.poywalletbackend.common.Constants.*;

/**
 * Utility class for Jwt related tasks
 */
@Slf4j(topic = "JwtProvider")
@Component
public class JwtProvider {
    @Value("${app.security.jwt.secret-key}")
    private String jwtSecret;
    @Value("${app.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${app.security.jwt.refresh-expiration}")
    private long jwtRefreshExpiration;

    // generate token
    public String generateJwt(Authentication authentication) {
        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Map<String, Object> extraClaims = new HashMap<>();

        // Build Jwt
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // generate refresh token
//    public String generateRefreshJwt(String username) {
//        Map<String, Object> extraClaims = new HashMap<>();
//
//        return Jwts.builder()
//                .setClaims(extraClaims)
//                .setSubject(username)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + jwtRefreshExpiration))
//                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }

    // validate token
    public boolean validateJwt(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token);
        } catch (SignatureException e) {
            log.error(INVALID_JWT_SIGN, e.getMessage());
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

    // Extract Username
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract Claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract All Claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Get SignInKey from string
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /*
    // valid refresh token
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // expired refresh token
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    */
}

