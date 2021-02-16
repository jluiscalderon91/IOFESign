package com.apsout.electronictestimony.api.util.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.apsout.electronictestimony.api.util.statics.SecurityConstants.EXPIRATION_TIME;
import static com.apsout.electronictestimony.api.util.statics.SecurityConstants.SECRET;

@Component
public class JWTTokenUtil implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;

    public String getSubjectOf(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        return getSubjectOf(token);
    }

    public String getSubjectOf(String token) {
        return getClaimOf(token, Claims::getSubject);
    }

    public Date getIssuedAtDateOf(String token) {
        return getClaimOf(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateOf(String token) {
        return getClaimOf(token, Claims::getExpiration);
    }

    public <T> T getClaimOf(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsOf(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsOf(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateOf(token);
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        return expirationDate.before(now);
    }

    public static String buildBy(String subject) {
        Map<String, Object> claims = new HashMap<>();
        Date createdDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Date expirationDate = calculateExpirationDate(createdDate);
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    private static Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + EXPIRATION_TIME);
    }
}
