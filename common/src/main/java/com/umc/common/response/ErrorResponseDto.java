package com.umc.common.response;

import com.umc.common.error.ValidationError;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ErrorResponseDto {
    private final String code;
    private final List<ValidationError> errors;
    private final String message;

    @Builder
    public ErrorResponseDto(String code, List<ValidationError> errors, String message) {
        this.code = code;
        this.errors = errors;
        this.message = message;
    }
}
