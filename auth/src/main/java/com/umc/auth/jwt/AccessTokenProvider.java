package com.umc.auth.jwt;


import com.umc.auth.dto.TokenDto;
import com.umc.auth.repository.RefreshTokenRedisRepository;
import com.umc.common.TokenValidation;
import com.umc.common.error.code.AuthErrorCode;
import com.umc.common.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessTokenProvider {

    private final TokenProvider tokenProvider;
    private final TokenValidation tokenValidation;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;


    public TokenDto issueAccessToken(String refreshToken) {

        if (!tokenValidation.validateToken(refreshToken) || refreshTokenRedisRepository.findByRefreshToken(refreshToken) == null) {
            throw new CustomException(AuthErrorCode.INVALID_REFRESH_TOKEN_FORMAT);
        } else {
            TokenDto tokenDto = tokenProvider.reIssueAccessToken(refreshToken);
            return tokenDto;
        }
    }
}
