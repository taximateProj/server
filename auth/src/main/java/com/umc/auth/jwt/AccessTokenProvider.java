package com.umc.auth.jwt;


import com.umc.auth.dto.TokenDto;
import com.umc.common.TokenValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessTokenProvider {

    private final TokenProvider tokenProvider;
    private final TokenValidation tokenValidation;


    public TokenDto issueAccessToken(String refreshToken) {

        if (!tokenValidation.validateToken(refreshToken)) {
            return null;
        } else {
            TokenDto tokenDto = tokenProvider.reIssueAccessToken(refreshToken);
            return tokenDto;
        }
    }
}
