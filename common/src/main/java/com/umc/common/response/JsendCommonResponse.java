package com.umc.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class JsendCommonResponse<T> {
    private String status;
    private T data;

    public static <T> JsendCommonResponse<T> success(T data) {
        return new JsendCommonResponse<>("success", data);
    }

    public static <T> JsendCommonResponse<T> fail(T data) {
        return new JsendCommonResponse<>("fail", data);
    }

    public static <T> JsendCommonResponse<T> error(T data) {
        return new JsendCommonResponse<>("error", data);
    }
}
