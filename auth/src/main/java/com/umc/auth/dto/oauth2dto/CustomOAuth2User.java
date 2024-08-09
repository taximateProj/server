package com.umc.auth.dto.oauth2dto;


import com.umc.auth.dto.memberdto.AuthMemberDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;




public class CustomOAuth2User implements OAuth2User {

    private final AuthMemberDto memberDto;

    public CustomOAuth2User(AuthMemberDto memberDto) {
        this.memberDto = memberDto;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return memberDto.getRole();
            } // String 만 받을 수 있나? -> authmemberdto 넣을 때 role string으로 넣고, AuthMember 엔티티로 변환할 때 converter로 enum으로 바꾸기
        });
        return collection;
    }

    @Override
    public String getName() {
        return memberDto.getUsername();
    }

}


