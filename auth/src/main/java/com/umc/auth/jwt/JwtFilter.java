package com.umc.auth.jwt;


import com.umc.auth.dto.TokenDto;

import com.umc.common.TokenValidation;
import com.umc.common.error.code.AuthErrorCode;
import com.umc.common.error.exception.CustomException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
@ComponentScan(basePackages = {"com.umc.common"})
public class JwtFilter extends OncePerRequestFilter {

    private static final String ACCESS_HEADER = "AccessToken";
    private static final String REFRESH_HEADER = "RefreshToken";

    private final TokenProvider tokenProvider;
    private final TokenValidation tokenValidation;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isRequestPassURI(request)) {
            System.out.println("passing jwtFilter");
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = getTokenFromHeader(request, ACCESS_HEADER);

        if (!tokenValidation.validateToken(accessToken)) {
            throw new CustomException(AuthErrorCode.INVALID_TOKEN_FORMAT);

        } else if (!tokenValidation.validateExpiredToken(accessToken)) {
            throw new CustomException(AuthErrorCode.TOKEN_EXPIRED);

        } else if (tokenValidation.validateToken(accessToken) && tokenValidation.validateExpiredToken(accessToken)) {
             SecurityContextHolder.getContext().setAuthentication(tokenProvider.getAuthentication(accessToken));
         }

        filterChain.doFilter(request, response);
    }

    Boolean isRequestPassURI(HttpServletRequest request) throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/access_token") || request.getRequestURI().startsWith("/kakao_logout") || request.getRequestURI().startsWith("/login")) {
            return true;
        }
        return false;
    }

    public String getTokenFromHeader(HttpServletRequest request, String ACCESS_HEADER) throws ServletException, IOException {
        String token = request.getHeader(ACCESS_HEADER);
        if (StringUtils.hasText(token)) {
            return token;
        }
        return null;
    }


    public static void redirectReissueURI(HttpServletRequest request, HttpServletResponse response, TokenDto tokenDto) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("accessToken", tokenDto.getAccessToken());
        session.setAttribute("refreshToken", tokenDto.getRefreshToken());
        response.sendRedirect("api/sign/reissue");
    }
}
