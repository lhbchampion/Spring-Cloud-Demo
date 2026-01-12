package com.lhb.base.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class JwtUtil {

    private final JwtConfig jwtConfig;

    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    /** 生成 Token */
    public String generateToken(String userId, Map<String, Object> claims) {
        Date now = new Date();
        Map<String, Object> payload = claims == null ? new HashMap<>() : claims;

        return Jwts.builder()
                .setSubject(userId)
                .setIssuer(jwtConfig.getIssuer())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtConfig.getExpire()))
                .setId(UUID.randomUUID().toString())
                .addClaims(payload)
                .signWith(jwtConfig.getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /** 强校验解析 */
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtConfig.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /** 校验 Token 是否合法 */
    public boolean verify(String token) {
        try {
            parseToken(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("JWT已过期");
        } catch (JwtException e) {
            log.warn("JWT非法: {}", e.getMessage());
        }
        return false;
    }

    /** 是否过期 */
    public boolean isTokenExpired(String token) {
        try {
            return parseToken(token).getExpiration().before(new Date());
        } catch (JwtException e) {
            return true;
        }
    }

    /** 刷新 Token（必须未过期） */
    public String refreshToken(String token) {
        Claims claims = parseToken(token);

        if (claims.getExpiration().before(new Date())) {
            throw new RuntimeException("Token已过期，不能刷新");
        }

        Map<String, Object> newClaims = new HashMap<>();
        claims.forEach((k, v) -> {
            if (!Claims.EXPIRATION.equals(k)
                    && !Claims.ISSUED_AT.equals(k)
                    && !Claims.ISSUER.equals(k)
                    && !Claims.SUBJECT.equals(k)
                    && !Claims.ID.equals(k)) {
                newClaims.put(k, v);
            }
        });

        return generateToken(claims.getSubject(), newClaims);
    }

    public String getUserId(String token) {
        return parseToken(token).getSubject();
    }

    public Object getClaim(String token, String name) {
        return parseToken(token).get(name);
    }

    public Date getExpiration(String token) {
        return parseToken(token).getExpiration();
    }

    public long getExpirationTime(String token) {
        return getExpiration(token).getTime();
    }

    public long getRemainingSeconds(String token) {
        Date expiration = getExpiration(token);
        long remainingMillis = expiration.getTime() - System.currentTimeMillis();
        return Math.max(remainingMillis / 1000, 0);
    }

    public long getExpireSeconds() {
        return jwtConfig.getExpire() / 1000;
    }

}
