package com.example.backend.global.provider;

import com.example.backend.user.security.CustomUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @Value("${jwt.accessTokenExpirationTime}")
    private Long jwtAccessTokenExpirationTime;

    @Value("${jwt.refreshTokenExpirationTime}")
    private Long jwtRefreshTokenExpirationTime;

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    // JWT 서명을 위한 Key 생성
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 사용자 인증 정보를 바탕으로 Access Token 생성
    public String generateAccessToken(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Date expiryDate = new Date(new Date().getTime() + jwtAccessTokenExpirationTime);

        return Jwts.builder()
                .setSubject(customUserDetails.getUsername())
                .claim("user-id", customUserDetails.getId())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Date expiryDate = new Date(new Date().getTime() + jwtRefreshTokenExpirationTime);

        return Jwts.builder()
                .setSubject(customUserDetails.getUsername())
                .claim("user-id", customUserDetails.getId())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }


    public Long getUserIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("user-id", Long.class);
    }

    // JWT 유효성 검증
    public Boolean validateToken(String token) {
        try {
            Jwts
                    .parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            return true;
        } // 형식이 잘못된 JWT (예: 구조가 틀리거나 Base64 디코딩 실패 등)
        catch (MalformedJwtException ex) {
            logger.warn("Invalid JWT token");
        }

        // 토큰이 만료된 경우 (exp 시간이 현재보다 이전)
        catch (ExpiredJwtException ex) {
            logger.warn("Expired JWT token");
        }

        // 지원하지 않는 JWT 형식 (예: 비정상적인 서명 알고리즘 등)
        catch (UnsupportedJwtException ex) {
            logger.warn("Unsupported JWT token");
        }

        // 토큰이 null이거나 비어 있거나, claim 문자열이 비정상일 경우
        catch (IllegalArgumentException ex) {
            logger.warn("JWT claims string is empty.");
        }

        return false; // 위 예외 중 하나라도 발생하면 유효하지 않음
    }

    public String generateTestAccessToken(String username, Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000); // 1시간 유효

        return Jwts.builder()
                .setSubject(Long.toString(userId))
                .claim("username", username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey())  // secretKey는 실제 사용하는 값과 동일해야 함
                .compact();
    }
}
