package com.umc.auth.service;


import com.umc.auth.dto.oauth2dto.CustomOAuth2User;
import com.umc.auth.dto.oauth2dto.KakaoResponseDto;
import com.umc.auth.dto.oauth2dto.OAuth2Response;
import com.umc.member.domain.Member;
import com.umc.member.domain.enums.Role;
import com.umc.member.dto.MemberDto;
import com.umc.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KakaoMemberDetailsService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2Response oAuth2Response = new KakaoResponseDto(oAuth2User.getAttributes()); // 이메일 처리
        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        System.out.println(username);

        Member existData = memberRepository.findByUsername(username);
        System.out.println(existData);

        String role = "ROLE_USER";

        if (existData == null) {
            System.out.println("I first time login!");


            memberRepository.save(Member.builder()
                    .role(Role.USER)
                    .email(oAuth2Response.getEmail())
                    .username(username)
                    .build());

            MemberDto memberDto = new MemberDto();
            memberDto.setUsername(username);
            memberDto.setRole(role);

            return new CustomOAuth2User(memberDto);



        } else { // 왜 있을 때는 다시 set 하지?? 그냥 이미 있는 유저라고 반환해야되는거 아닌가? -> login 하면서 회원 정보 update 해주는 정책 (이거는 정해야할듯?)
            System.out.println("I login!");
            existData.setUsername(username);
            existData.setEmail(oAuth2Response.getEmail());
            memberRepository.save(existData);

            MemberDto memberDto = new MemberDto();
            memberDto.setUsername(username);
            memberDto.setRole(role);

            return new CustomOAuth2User(memberDto);
        }

//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(member.getRole().name());
//
//        return new KakaoMemberDetails(String.valueOf(member.getEmail()),
//                Collections.singletonList(authority),
//                oAuth2User.getAttributes());
    }
}
