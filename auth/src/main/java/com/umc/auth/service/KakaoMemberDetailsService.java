package com.umc.auth.service;


import com.umc.auth.dto.memberdto.AuthMemberDto;
import com.umc.auth.dto.oauth2dto.CustomOAuth2User;
import com.umc.auth.dto.oauth2dto.KakaoMemberDetails;
import com.umc.auth.dto.oauth2dto.KakaoResponseDto;
import com.umc.auth.dto.oauth2dto.OAuth2Response;
import com.umc.auth.entity.AuthMember;
import com.umc.auth.entity.enums.Role;
import com.umc.auth.repository.AuthMemberRepository;
import com.umc.common.error.code.AuthErrorCode;
import com.umc.common.error.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoMemberDetailsService extends DefaultOAuth2UserService {

    private final AuthMemberRepository authmemberRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        if (oAuth2User == null) {
            log.error("OAuth2User is null");
        }


//        System.out.println(oAuth2User.getAttributes());
//        KakaoUserInfo oAuth2Response = new KakaoUserInfo(oAuth2User.getAttributes()); //email 데이터의 구조 때문에 따로 뺌
        OAuth2Response oAuth2Response = new KakaoResponseDto(oAuth2User.getAttributes());
        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        log.info("Username is {}", username);


        AuthMember existData = authmemberRepository.findByUsername(username);
        System.out.println(existData);
        System.out.println("Kakao Member Details printing role.......");
//        log.info("user's username is {}", existData.getUsername());
//        log.info("user's uuid is {}", existData.getUuid());
//        log.info("user's role is {}", existData.getRole());




        if (existData == null) {
            System.out.println("I first time login!");


            authmemberRepository.save(AuthMember.builder()
                    .role(Role.NOT_SIGNUP_USER)
                    .email(oAuth2Response.getEmail())
                    .username(username)
                    .build());

            AuthMemberDto authmemberDto = new AuthMemberDto();
            authmemberDto.setUsername(username);
            authmemberDto.setRole("NOT_SIGNUP_USER");

            return new CustomOAuth2User(authmemberDto);

        } else if (existData.getRole().equals(Role.NOT_SIGNUP_USER)) {
            AuthMemberDto authmemberDto = new AuthMemberDto();
            authmemberDto.setUuid(existData.getUuid());
            authmemberDto.setUsername(username);
            authmemberDto.setRole("NOT_SIGNUP_USER");
            authmemberDto.setEmail(existData.getEmail());
            // 가입되지 않은 유저 - 가입 화면으로 리다이렉트
            return new CustomOAuth2User(authmemberDto);

        } else { // 왜 있을 때는 다시 set 하지?? 그냥 이미 있는 유저라고 반환해야되는거 아닌가? -> login 하면서 회원 정보 update 해주는 정책 (이거는 정해야할듯?)
            System.out.println("I login!");
            existData.setUsername(username);
            existData.setEmail(oAuth2Response.getEmail());
            authmemberRepository.save(existData);

            AuthMemberDto authMemberDto = new AuthMemberDto();
            authMemberDto.setUsername(username);

            String role;
            if (existData.getRole().equals(Role.USER)) {
                role = "user";
            } else {
                role = "admin";
            }
            authMemberDto.setRole(role);
            authMemberDto.setUuid(existData.getUuid());

            return new CustomOAuth2User(authMemberDto);
        }

//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(member.getRole().name());
//
//        return new KakaoMemberDetails(String.valueOf(member.getEmail()),
//                Collections.singletonList(authority),
//                oAuth2User.getAttributes());
    }
}

