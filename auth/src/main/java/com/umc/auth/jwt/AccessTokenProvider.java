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

        if (!tokenValidation.validateToken(refreshToken)) {
            throw new CustomException(AuthErrorCode.INVALID_REFRESH_TOKEN_FORMAT);
        } else if (!tokenValidation.validateExpiredToken(refreshToken))  {
            throw new CustomException(AuthErrorCode.REFRESH_TOKEN_EXPIRED);
        } else if (refreshTokenRedisRepository.findByRefreshToken(refreshToken) == null) {
            throw new CustomException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND);
        } else {
            TokenDto tokenDto = tokenProvider.reIssueAccessToken(refreshToken);
            return tokenDto;
        }
    }

    public TokenDto reissueAccessToken(String refreshToken, String accessToken) {
        if (!tokenValidation.validateToken(accessToken)) {
            throw new CustomException(AuthErrorCode.INVALID_TOKEN_FORMAT);
        } else if (!tokenValidation.validateToken(refreshToken)) {
            throw new CustomException(AuthErrorCode.INVALID_REFRESH_TOKEN_FORMAT);
        } else if (!tokenValidation.validateExpiredToken(refreshToken))  {
            throw new CustomException(AuthErrorCode.REFRESH_TOKEN_EXPIRED);
        } else if (refreshTokenRedisRepository.findByRefreshToken(refreshToken) == null) {
            throw new CustomException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND);
        } else {
            TokenDto tokenDto = tokenProvider.reIssueAccessToken(refreshToken);
            return tokenDto;
        }
    }


}
