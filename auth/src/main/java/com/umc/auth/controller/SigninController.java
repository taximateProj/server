package com.umc.auth.controller;


import com.umc.auth.dto.TokenDto;
import com.umc.auth.oauth2.OAuth2SuccessHandler;
import com.umc.common.error.code.AuthErrorCode;
import com.umc.common.error.exception.CustomException;
import com.umc.common.response.JsendCommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sign")
public class SigninController {

    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @GetMapping("/login/kakao")
    public JsendCommonResponse<TokenDto> loginKakao(@RequestParam(name = "accessToken") String accessToken,
                                     @RequestParam(name = "refreshToken") String refreshToken) {
        return JsendCommonResponse.success(new TokenDto(accessToken, refreshToken));
    }


    @GetMapping("/reissue")
    public JsendCommonResponse<TokenDto> reissueToken(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String accessToken = (String) session.getAttribute("accessToken");
        String refreshToken = (String) session.getAttribute("refreshToken");
        response.addHeader("Authorization", accessToken);
        response.addCookie(oAuth2SuccessHandler.createCookie("refreshToken", refreshToken));

        return JsendCommonResponse.success(new TokenDto(accessToken, refreshToken));
    }
}
