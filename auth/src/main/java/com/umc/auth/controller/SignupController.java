package com.umc.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.auth.dto.SignupEventMessageDto;

import com.umc.auth.dto.memberdto.MemberJoinDto;
import com.umc.common.TokenValidation;
import com.umc.auth.service.SignupService;
import com.umc.common.error.code.AuthErrorCode;
import com.umc.common.error.exception.CustomException;
import com.umc.common.response.JsendCommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;
    private final TokenValidation tokenValidation;

    @PostMapping("/signup")
    public JsendCommonResponse<SignupEventMessageDto> SignUp(HttpServletRequest request) throws IOException{
        // 레포지토리에 저장되어 있는 멤버불러와서 같이 dto로 넘기는 방식?
//        AuthMember authMember = signupService.joinMember(request);
        // 헤더에 access 토큰 있다고 가정 - 헤더의 토큰 롤 검사 -> 롤이 not sign up user 이면 가입 절차 진행
        String accessToken = request.getHeader("Authorization"); // jwt 열어서 롤 확인해야되는데 어떻게 하지?
        String role = tokenValidation.getRole(accessToken); // role 가져오기 -> not ~user 맞는지 검사
        String uuid = tokenValidation.getUuid(accessToken); // username 가져오기

        if (!role.equals("not_signup_user")) {
            throw new CustomException(AuthErrorCode.ALREADY_SIGNEDUP_USER);
        }
        // MemberJoinDto로 만들기 -> 서비스 단에 전달
        MemberJoinDto memberJoinDto = memberJoinConverter(request); // request -> MemberJoinDto

        SignupEventMessageDto signupEventMessageDto = signupService.joinMember(memberJoinDto, uuid);

        return JsendCommonResponse.success(signupEventMessageDto);
    }

    private MemberJoinDto memberJoinConverter(HttpServletRequest request) throws IOException {

        String jsonData = request.getReader().lines().collect(Collectors.joining());
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(jsonData, MemberJoinDto.class);
    }
}
