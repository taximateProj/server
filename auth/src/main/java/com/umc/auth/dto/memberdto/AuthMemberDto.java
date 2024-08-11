package com.umc.auth.dto.memberdto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AuthMemberDto {
    private UUID uuid;
    private String username;
    private String role;
    private String email;
}
