package com.umc.auth.service;

import com.example.auth_practice.domain.AuthMember;
import com.example.auth_practice.dto.SignupEventMessageDto;
import com.example.auth_practice.dto.memberdto.MemberJoinDto;
import com.example.auth_practice.repository.AuthMemberRepository;
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
