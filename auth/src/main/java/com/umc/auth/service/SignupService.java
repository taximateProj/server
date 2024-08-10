package com.umc.auth.service;


import com.umc.auth.dto.SignupEventMessageDto;
import com.umc.auth.dto.memberdto.MemberJoinDto;
import com.umc.auth.entity.AuthMember;
import com.umc.auth.repository.AuthMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final AuthMemberRepository authMemberRepository;

    @Transactional
    public SignupEventMessageDto joinMember(MemberJoinDto request, String uuid) {

        AuthMember authMember = authMemberRepository.findByUuid(UUID.fromString(uuid));

        return SignupEventMessageDto.builder()
                .uuid(UUID.fromString(uuid))
                .name(request.getName())
                .email(authMember.getEmail())
                .role(authMember.getRole())
                .username(authMember.getUsername())
                .school(request.getSchool())
                .accountNumber(request.getAccountNumber())
                .build();
    }


}
