package com.umc.common.error.code;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CommonErrorCode implements ErrorCode {

    // 서버 및 시스템 관련 에러
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SYS-001", "서버 내부 오류"),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "SYS-002", "서비스 이용 불가"),
    GATEWAY_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "SYS-003", "요청 시간 초과"),

    // 클라이언트 관련 에러
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "CLIENT-001", "잘못된 요청"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "CLIENT-002", "인증 필요"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "CLIENT-003", "접근 거부"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "CLIENT-004", "리소스를 찾을 수 없음"),
    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "CLIENT-005", "요청이 너무 많음"),

    // 카프카 관련 에러
    KAFKA_CONNECTION_FAILED(HttpStatus.SERVICE_UNAVAILABLE, "KAFKA-001", "카프카 연결 실패"),
    KAFKA_MESSAGE_PRODUCE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "KAFKA-002", "메시지 전송 실패"),
    KAFKA_MESSAGE_CONSUME_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "KAFKA-003", "메시지 수신 실패"),
    KAFKA_TOPIC_NOT_FOUND(HttpStatus.NOT_FOUND, "KAFKA-004", "토픽을 찾을 수 없음"),

    // 웹소켓 관련 에러
    WEBSOCKET_CONNECTION_FAILED(HttpStatus.SERVICE_UNAVAILABLE, "WEBSOCKET-001", "웹소켓 연결 실패"),
    WEBSOCKET_MESSAGE_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "WEBSOCKET-002", "메시지 전송 실패"),
    WEBSOCKET_MESSAGE_RECEIVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "WEBSOCKET-003", "메시지 수신 실패"),
    WEBSOCKET_SESSION_NOT_FOUND(HttpStatus.NOT_FOUND, "WEBSOCKET-004", "세션을 찾을 수 없음");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
