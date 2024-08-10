package com.umc.auth.controller;

import com.umc.auth.dto.TokenDto;
import com.umc.auth.jwt.AccessTokenProvider;
import com.umc.auth.jwt.JwtFilter;
import com.umc.auth.jwt.TokenProvider;
import com.umc.auth.oauth2.OAuth2SuccessHandler;
import com.umc.common.response.JsendCommonResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
public class TokenController {

    private final TokenProvider tokenProvider;
    private final JwtFilter jwtFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final AccessTokenProvider accessTokenProvider;
    private static final String REFRESH_HEADER = "RefreshToken";
    private static final String ACCESS_HEADER = "AccessToken";

    @GetMapping("/access_token")
    public ResponseEntity<TokenDto> access_token(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String refreshToken = request.getHeader(REFRESH_HEADER);
        System.out.println(refreshToken);
        TokenDto tokenDto = accessTokenProvider.issueAccessToken(refreshToken);
        response.addHeader(ACCESS_HEADER, tokenDto.getAccessToken());
        return ResponseEntity.ok(tokenDto);
    }
}
