package com.umc.auth.dto;



import com.umc.auth.entity.enums.Role;
import lombok.Builder;

import java.util.UUID;

@Builder
public class SignupEventMessageDto { // 카프카로 보낼 메시지
    private UUID uuid; // 생성하기

    private String username; // 카카오에서 온 정보는 이걸로 확인

    private String name;

    private String email;

    private String school;

    private String accountNumber;

    private Role role;
}
