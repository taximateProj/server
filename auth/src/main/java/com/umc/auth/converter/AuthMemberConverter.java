package com.umc.auth.converter;


import com.umc.auth.dto.memberdto.AuthMemberDto;
import com.umc.auth.entity.AuthMember;
import com.umc.auth.entity.enums.Role;

public class AuthMemberConverter { // AuthMemberDto <-> AuthMember : role 변환

    public static AuthMember toAuthMember(AuthMemberDto authMemberDto) { // AuthMemberDto -> AuthMember
        String String_role = authMemberDto.getRole();
        Role role = null;
        if (role.equals("admin")) {
            role = Role.ADMIN;
        } else if (role.equals("user")) {
            role = Role.USER;
        } else if (role.equals("not_signup_user")) {
            role = Role.NOT_SIGNUP_USER;
        }

        return AuthMember.builder()
                .uuid(authMemberDto.getUuid())
                .email(authMemberDto.getEmail())
                .username(authMemberDto.getUsername())
                .role(role)
                .build();
    }
}
