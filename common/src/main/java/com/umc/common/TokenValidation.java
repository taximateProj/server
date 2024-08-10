package com.umc.common;



import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class TokenValidation {

    private final String secretKey_string;
    private static final String AUTH_KEY = "AUTHORITY";
    private static final String AUTH_UUID = "UUID";

    private Key secretkey;

    public TokenValidation(@Value("${spring.jwt.secret_key}") String secretKey) {
        this.secretKey_string = secretKey;
    }

    @PostConstruct
    public void initKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey_string);
        this.secretkey = Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰 유효성 검사 - 토큰의 무결성 & 서명 검증 - 위조되지 않았는지
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretkey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SignatureException | MalformedJwtException e) {
            return false;
        } catch (UnsupportedJwtException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // 토큰 만료여부 검사 - 토큰 만료됐으면 리턴
    public boolean validateExpiredToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretkey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    //uuid 가져오는 메서드
    public String getRole(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretkey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get(AUTH_KEY, String.class);
    }

    public String getUuid(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretkey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get(AUTH_UUID, String.class);
    }


}
