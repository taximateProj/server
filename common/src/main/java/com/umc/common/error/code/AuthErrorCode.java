package com.umc.common.error.code;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum AuthErrorCode implements ErrorCode{

    // 인증 및 권한 관련 에러
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "AUTH-001", "사용자 인증에 실패했습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "AUTH-002", "접근이 거부되었습니다. 이 리소스에 대한 권한이 없습니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "AUTH-003", "로그인에 실패했습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH-004", "토큰의 유효기간이 만료되었습니다."),
    INVALID_TOKEN_FORMAT(HttpStatus.BAD_REQUEST, "AUTH-005", "잘못된 토큰 형식입니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "AUTH-006", "쿠키에 refreshToken이 없습니다."),
    INVALID_REFRESH_TOKEN_FORMAT(HttpStatus.BAD_REQUEST, "AUTH-007", "잘못된 리프레쉬 토큰 형식입니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH-008", "리프레쉬 토큰의 유효기간이 만료되었습니다."),
    // 추가 가입 절차
    ALREADY_SIGNEDUP_USER(HttpStatus.FORBIDDEN, "SIGNUP-001", "이미 가입된 사용자 입니다."),
    NOT_SIGNEDUP_USER(HttpStatus.FORBIDDEN, "SIGNUP-002", "아직 가입이 완료되지 않은 사용자 입니다. "),
    MEMBER_NOT_FOUND(HttpStatus.FORBIDDEN, "SIGNUP-003", "멤버를 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
