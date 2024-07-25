package com.umc.common.error.code;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ChatErrorCode implements ErrorCode{
    // 채팅 관련 에러
    MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "CHAT-001", "메시지를 찾을 수 없습니다."),
    USER_NOT_IN_CHAT(HttpStatus.FORBIDDEN, "CHAT-002", "사용자가 채팅방에 없습니다."),
    CHATROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "CHAT-003", "채팅방을 찾을 수 없습니다."),
    MESSAGE_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "CHAT-004", "메시지 전송에 실패했습니다."),
    INVALID_CHATROOM_ID(HttpStatus.BAD_REQUEST, "CHAT-005", "잘못된 채팅방 ID입니다."),
    USER_BANNED_FROM_CHAT(HttpStatus.FORBIDDEN, "CHAT-006", "사용자가 채팅방에서 차단되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
