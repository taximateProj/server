package com.umc.auth.oauth2;


import com.umc.auth.dto.TokenDto;
import com.umc.auth.dto.oauth2dto.CustomOAuth2User;
import com.umc.auth.entity.AuthMember;
import com.umc.auth.entity.RefreshToken;
import com.umc.auth.entity.enums.Role;
import com.umc.auth.jwt.TokenProvider;
import com.umc.auth.repository.AuthMemberRepository;
import com.umc.auth.repository.RefreshTokenRedisRepository;
import com.umc.common.error.code.AuthErrorCode;
import com.umc.common.error.exception.CustomException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String REDIRECT_URI = "http://localhost:8080/api/sign/login/kakao?accessToken=%s&refreshToken=%s";
    private final TokenProvider tokenProvider;
    private final AuthMemberRepository memberRepository;
    private final RefreshTokenRedisRepository refreshTokenRepository;

    //로그인 성공 후 처리할 로직 - JWT 토큰 생성 후 응답 헤더에 추가
    @Transactional
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        System.out.println(customOAuth2User.getName());

        String username = customOAuth2User.getName();

        AuthMember member = memberRepository.findByUsername(username);// username이 키
        if (member == null) {
            throw new CustomException(AuthErrorCode.ALREADY_SIGNEDUP_USER);
        }

        TokenDto tokenDto = tokenProvider.createToken(member.getUuid().toString(), member.getRole().name());

        saveRefreshTokenOnRedis(member, tokenDto);

        response.addCookie(createCookie("Authorization", tokenDto.getRefreshToken())); // token 어떤 식으로 넘겨줘야 되는지?

        if (member.getRole().equals(Role.NOT_SIGNUP_USER)) {
            response.sendRedirect("http://localhost:3000/signup");
        } else {
            response.sendRedirect("http://localhost:3000/");
        }


//        String redirectURI = String.format(REDIRECT_URI, tokenDto.getAccessToken(), tokenDto.getRefreshToken());
//        System.out.println(tokenDto.getAccessToken());

//        response.addHeader("Authorization", tokenDto.getAccessToken());



//        getRedirectStrategy().sendRedirect(request, response, redirectURI);

    }

    private void saveRefreshTokenOnRedis(AuthMember member, TokenDto tokenDto) {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority(member.getRole().name()));
        refreshTokenRepository.save(RefreshToken.builder()
                .id(member.getUuid().toString())
                .authorities(simpleGrantedAuthorities)
                .refreshToken(tokenDto.getRefreshToken())
                .build());
    }

    public Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(true); // js가 해당 쿠키 가져가지 못하게

        return cookie;
    }


}
