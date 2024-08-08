package com.umc.auth.controller;

import com.umc.auth.dto.TokenDto;
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
public class SignInController {

    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @GetMapping("/login/kakao")
    public ResponseEntity loginKakao(@RequestParam(name = "accessToken") String accessToken,
                                     @RequestParam(name = "refreshToken") String refreshToken) {
        return new ResponseEntity(TokenDto.of(accessToken, refreshToken), HttpStatus.OK);
    }

    @GetMapping("/reissue")
    public ResponseEntity reissueToken(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String accessToken = (String) session.getAttribute("accessToken");
        String refreshToken = (String) session.getAttribute("refreshToken");
        response.addHeader("Authorization", accessToken);
        response.addCookie(oAuth2SuccessHandler.createCookie("refreshToken", refreshToken));

        return new ResponseEntity(new TokenDto(accessToken, refreshToken), HttpStatus.OK);
    }
}
