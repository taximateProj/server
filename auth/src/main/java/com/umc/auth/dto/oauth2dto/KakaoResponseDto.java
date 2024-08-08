package com.umc.auth.dto.oauth2dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class KakaoResponseDto implements OAuth2Response { // 이메일 불러와서 변환하는데 쓰임

    public static final String KAKAO_ACCOUNT = "kakao_account";
    public static final String EMAIL = "email";

    private Map<String, Object> attributes;

    public KakaoResponseDto(Map<String, Object> attributes) {
        this.attributes = attributes;
    }


    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }


    @Override
    public String getEmail() {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>(){};

        Object kakaoAccount = attributes.get(KAKAO_ACCOUNT);
        Map<String, Object> account = objectMapper.convertValue(kakaoAccount, typeRef);

        return (String) account.get(EMAIL);
    }


}
