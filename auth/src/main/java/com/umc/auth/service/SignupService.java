package com.umc.auth.service;


import com.umc.auth.dto.SignupEventMessageDto;
import com.umc.auth.dto.memberdto.MemberJoinDto;
import com.umc.auth.entity.AuthMember;
import com.umc.auth.repository.AuthMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final AuthMemberRepository authMemberRepository;

    @Transactional
    public SignupEventMessageDto joinMember(MemberJoinDto request, String username) {

        AuthMember authMember = authMemberRepository.findByUsername(username);

        return SignupEventMessageDto.builder()
                .uuid(authMember.getUuid())
                .name(request.getName())
                .email(authMember.getEmail())
                .role(authMember.getRole())
                .username(username)
                .school(request.getSchool())
                .accountNumber(request.getAccountNumber())
                .build();
    }


}
