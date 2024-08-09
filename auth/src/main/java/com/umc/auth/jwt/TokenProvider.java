package com.umc.auth.jwt;


import com.umc.auth.dto.TokenDto;
import com.umc.auth.dto.oauth2dto.KakaoMemberDetails;
import com.umc.auth.entity.RefreshToken;
import com.umc.auth.repository.RefreshTokenRedisRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private static final String AUTH_KEY = "AUTHORITY";
    private static final String AUTH_UUID = "UUID";

    private final String secretKey_string;
    private final long accessTokenValidityMilliSeconds;
    private final long refreshTokenValidityMilliSeconds;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    private Key secretkey;


    public TokenProvider(@Value("${spring.jwt.secret_key}") String secretKey,
                         @Value("${spring.jwt.access-token-validity-in-seconds}") long accessTokenValiditySeconds,
                         @Value("${spring.jwt.refresh-token-validity-in-seconds}") long refreshTokenValiditySeconds, RefreshTokenRedisRepository refreshTokenRedisRepository) {
        this.secretKey_string = secretKey;
        this.accessTokenValidityMilliSeconds = accessTokenValiditySeconds * 1000;
        this.refreshTokenValidityMilliSeconds = refreshTokenValiditySeconds * 1000;
        this.refreshTokenRedisRepository = refreshTokenRedisRepository;
    }

    @PostConstruct
    public void initKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey_string);
        this.secretkey = Keys.hmacShaKeyFor(keyBytes);
    }

    //access, refresh Token 생성
    public TokenDto createToken(String uuid, String role) {
        long now = (new Date()).getTime();

        Date accessTokenValidity = new Date(now + this.accessTokenValidityMilliSeconds);
        Date refreshTokenValidity = new Date(now + this.refreshTokenValidityMilliSeconds);

        String accessToken = Jwts.builder()
                    .addClaims(Map.of(AUTH_UUID, uuid))
                .addClaims(Map.of(AUTH_KEY, role))
                .signWith(secretkey, SignatureAlgorithm.HS256)
                .setExpiration(accessTokenValidity)
                .compact();

        String refreshToken = Jwts.builder()
                .addClaims(Map.of(AUTH_UUID, uuid))
                .addClaims(Map.of(AUTH_KEY, role))
                .signWith(secretkey, SignatureAlgorithm.HS256)
                .setExpiration(refreshTokenValidity)
                .compact();

        return TokenDto.of(accessToken, refreshToken);

    }

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


    // 토큰으로부터 Authentication 객체 만들어서 리턴하는 메소드
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretkey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        System.out.print("claims: ");
        System.out.println(claims);

        List<String> authorities = Arrays.asList(claims.get(AUTH_KEY)
                .toString()
                .split(","));

        List<? extends GrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                .map(auth -> new SimpleGrantedAuthority(auth))
                .collect(Collectors.toList());


        KakaoMemberDetails principal = new KakaoMemberDetails(
                (String) claims.get(AUTH_UUID),
                simpleGrantedAuthorities,
                Map.of());

        return new UsernamePasswordAuthenticationToken(principal, token, simpleGrantedAuthorities);
    }

    @Transactional
    public TokenDto reIssueAccessToken(String refreshToken) {
        RefreshToken findToken = refreshTokenRedisRepository.findByRefreshToken(refreshToken);

        TokenDto tokenDto = createToken(findToken.getId(), findToken.getAuthority());
        refreshTokenRedisRepository.save(RefreshToken.builder()
                .id(findToken.getId())
                .authorities(findToken.getAuthorities())
                .refreshToken(tokenDto.getRefreshToken())
                .build());

        return tokenDto;
    }
}
