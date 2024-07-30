package com.umc.common.kafkamessage;

import lombok.Getter;

@Getter
public class CoreMessageDto<T> {
    private String eventType;
    private T data;
}
