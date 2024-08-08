package com.umc.common.jwtTokenValidator;


import com.umc.auth.repository.AuthMemberRepository;
import com.umc.auth.repository.RefreshTokenRedisRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.UUID;


@Component
public class TokenValidation {

    private final String secretKey_string;
    private final long accessTokenValidityMilliSeconds;
    private final long refreshTokenValidityMilliSeconds;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final TokenProvider tokenProvider;
    private final AuthMemberRepository authMemberRepository;
    private final AuthMemberRepository;

    private Key secretkey;

    public TokenValidation(@Value("${spring.jwt.secret_key}") String secretKey,
                           @Value("${spring.jwt.access-token-validity-in-seconds}") long accessTokenValiditySeconds,
                           @Value("${spring.jwt.refresh-token-validity-in-seconds}") long refreshTokenValiditySeconds, RefreshTokenRedisRepository refreshTokenRedisRepository, TokenProvider tokenProvider, AuthMemberRepository authMemberRepository) {
        this.secretKey_string = secretKey;
        this.accessTokenValidityMilliSeconds = accessTokenValiditySeconds * 1000;
        this.refreshTokenValidityMilliSeconds = refreshTokenValiditySeconds * 1000;
        this.refreshTokenRedisRepository = refreshTokenRedisRepository;
        this.tokenProvider = tokenProvider;
        this.authMemberRepository = authMemberRepository;
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

    public boolean isSignUpUser(String token) {
        String uuid = tokenProvider.getUuid(token);
        String role = tokenProvider.getRole(token);

        if (role == null) {
            return false;
        } else if (role.equals("not_signup_user")) {
            return false;
        } else if (!authMemberRepository.existByUuid(UUID.fromString(uuid))) {
            return false;
        }
        return true;

    }


}
