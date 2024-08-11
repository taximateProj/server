package com.umc.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.auth.dto.SignupEventMessageDto;

import com.umc.auth.dto.TokenDto;
import com.umc.auth.dto.memberdto.MemberJoinDto;
import com.umc.auth.entity.enums.Role;
import com.umc.auth.jwt.TokenProvider;
import com.umc.common.TokenValidation;
import com.umc.auth.service.SignupService;
import com.umc.common.error.code.AuthErrorCode;
import com.umc.common.error.exception.CustomException;
import com.umc.common.response.JsendCommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;
    private final TokenValidation tokenValidation;
    private final ObjectMapper objectMapper;
    private final TokenProvider tokenProvider;

//    SignupController(ObjectMapper objectMapper) {
//
//        this.objectMapper = objectMapper;
//    }

    @PostMapping("/signup")
    public ResponseEntity<TokenDto> SignUp(HttpServletRequest request) throws IOException{
        // 레포지토리에 저장되어 있는 멤버불러와서 같이 dto로 넘기는 방식?
//        AuthMember authMember = signupService.joinMember(request);
        // 헤더에 access 토큰 있다고 가정 - 헤더의 토큰 롤 검사 -> 롤이 not sign up user 이면 가입 절차 진행
        String accessToken = request.getHeader("AccessToken"); // jwt 열어서 롤 확인해야되는데 어떻게 하지?
        String refreshToken = request.getHeader("RefreshToken");
        System.out.println(accessToken);

        if (!tokenValidation.validateToken(refreshToken)) {
            throw new CustomException(AuthErrorCode.INVALID_REFRESH_TOKEN_FORMAT);
        } else if (!tokenValidation.validateExpiredToken(refreshToken)) {
            throw new CustomException(AuthErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        String uuid = tokenValidation.getUuid(accessToken);
        System.out.println(uuid);


        String role = tokenValidation.getRole(accessToken); // role 가져오기 -> not ~user 맞는지 검사
//        String uuid = tokenValidation.getUuid(accessToken); // username 가져오기

        log.info("role: {} and uuid:{}", role, uuid);
        System.out.println(role);
        System.out.println(uuid);


        log.info("make memberJoinDto");


        // MemberJoinDto로 만들기 -> 서비스 단에 전달
        MemberJoinDto memberJoinDto = objectMapper.readValue(request.getReader().lines().collect(Collectors.joining()), MemberJoinDto.class); // request -> MemberJoinDto

        SignupEventMessageDto signupEventMessageDto = signupService.joinMember(memberJoinDto, uuid);

        // 가져오는 것 완료!
        System.out.println(signupEventMessageDto);
        System.out.println(signupEventMessageDto.getSchool());
        System.out.println(signupEventMessageDto.getName());
        System.out.println(signupEventMessageDto.getGender());

        TokenDto tokenDto = tokenProvider.signupIssueToken(accessToken, refreshToken);

        return ResponseEntity.ok(tokenDto);
    }
}
