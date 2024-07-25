package com.umc.common.error.handler;

import com.umc.common.error.ValidationError;
import com.umc.common.error.code.CommonErrorCode;
import com.umc.common.error.code.ErrorCode;
import com.umc.common.error.exception.CustomException;
import com.umc.common.response.JsendCommonResponse;
import com.umc.common.response.ResponseFactory;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * CustomException을 처리
     */
    @ExceptionHandler(value = CustomException.class)
    public JsendCommonResponse<Object> handleCustomException(CustomException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        log.error("CustomException: code={}, message={}", errorCode.getCode(), errorCode.getMessage());
        return ResponseFactory.failure(errorCode);
    }

    /**
     * Valid로 검증된 객체의 유효성 검사가 실패할 때 발생
     */
    protected JsendCommonResponse<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ErrorCode errorCode = CommonErrorCode.BAD_REQUEST;

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ValidationError> errors = fieldErrors.stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        log.error("Validation error: {}", errors);
        return ResponseFactory.failure(errorCode, errors);
    }

    /**
     * PathVariable이나 RequestParam 등의 검증이 실패할 때 발생한다.
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    protected JsendCommonResponse<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        ErrorCode errorCode = CommonErrorCode.BAD_REQUEST;

        List<ValidationError> errors = ex.getConstraintViolations().stream()
                .map(violation -> new ValidationError(violation.getPropertyPath().toString(), violation.getMessage()))
                .toList();

        log.error("Constraint violation error: {}", errors);
        return ResponseFactory.failure(errorCode, errors);
    }

    /**
     * 요청 파라미터의 타입이 컨트롤러 메서드의 파라미터 타입과 일치하지 않을 때
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public JsendCommonResponse<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ErrorCode errorCode = CommonErrorCode.BAD_REQUEST;
        String errorMessage = String.format("Parameter '%s' should be of type '%s'", ex.getName(), ex.getRequiredType().getSimpleName());
        log.error("Method argument type mismatch: {}", errorMessage);
        return ResponseFactory.failure(errorCode, List.of(new ValidationError(ex.getName(), errorMessage)));
    }

    /**
     * 필수 요청 파라미터가 누락된 경우
     */
    protected JsendCommonResponse<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        ErrorCode errorCode = CommonErrorCode.BAD_REQUEST;
        log.error("Missing request parameter: {}", ex.getParameterName());
        return ResponseFactory.failure(errorCode);
    }

    /**
     * 요청된 URL에 대한 핸들러가 없는 경우
     */
    protected JsendCommonResponse<Object> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        ErrorCode errorCode = CommonErrorCode.NOT_FOUND;
        log.error("No handler found for URL: {}", ex.getRequestURL());
        return ResponseFactory.failure(errorCode);
    }

    /**
     * 위에서 처리되지 않은 예외가 발생할 때
     */
    @ExceptionHandler(Exception.class)
    public JsendCommonResponse<Object> handleAll(Exception ex) {
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        log.error("Unhandled exception: ", ex);
        return ResponseFactory.failure(errorCode);
    }
}
