package com.umc.taxisharing.domain.entity.money;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class Money{
    public static final Money ZERO = new Money(BigDecimal.ZERO);
    BigDecimal value;

    private Money(BigDecimal value) {
        this.value = value;
    }

    public static Money valueOf(BigDecimal value) {
        return new Money(value);
    }

    public static Money valueOf(long value) {
        return new Money(BigDecimal.valueOf(value));
    }
}
