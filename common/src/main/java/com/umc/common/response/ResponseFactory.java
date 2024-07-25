package com.umc.common.response;

import com.umc.common.error.ValidationError;
import com.umc.common.error.code.ErrorCode;

import java.util.List;

public class ResponseFactory {
    private ResponseFactory() {
        throw new UnsupportedOperationException("팩토리 유틸리티 메소드는 인스턴스화 할 수 없습니다.");
    }
    // ErrorResponse 생성 메서드
    public static JsendCommonResponse<Object> failure(ErrorCode errorCode) {
        return JsendCommonResponse.fail(errorCode.getMessage());
    }

    public static JsendCommonResponse<Object> failure(ErrorCode errorCode, List<ValidationError> errors) {
        return buildErrorResponse(errorCode, errors);
    }

    private static JsendCommonResponse<Object> buildErrorResponse(ErrorCode errorCode, List<ValidationError> errors) {
        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .errors(errors)
                .build();
        return JsendCommonResponse.error(errorResponse);
    }
}