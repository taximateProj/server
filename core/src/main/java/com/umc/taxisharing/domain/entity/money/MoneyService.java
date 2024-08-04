package com.umc.taxisharing.domain.entity.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyService {

    /**
     * 두 Money 객체의 금액이 동일한지 비교합니다.
     *
     * @param money1 첫 번째 Money 객체
     * @param money2 두 번째 Money 객체
     * @return 두 금액이 같으면 true, 그렇지 않으면 false
     */
    public boolean isEqual(Money money1, Money money2) {
        return BigDecimalUtils.is(money1.getValue()).equalTo(money2.getValue());
    }

    /**
     * money1이 money2보다 큰지 비교합니다.
     *
     * @param money1 첫 번째 Money 객체
     * @param money2 두 번째 Money 객체
     * @return money1이 더 크면 true, 그렇지 않으면 false
     */
    public boolean isGreaterThan(Money money1, Money money2) {
        return BigDecimalUtils.is(money1.getValue()).greaterThan(money2.getValue());
    }

    /**
     * money1이 money2보다 작은지 비교합니다.
     *
     * @param money1 첫 번째 Money 객체
     * @param money2 두 번째 Money 객체
     * @return money1이 더 작으면 true, 그렇지 않으면 false
     */
    public boolean isLessThan(Money money1, Money money2) {
        return BigDecimalUtils.is(money1.getValue()).lessThan(money2.getValue());
    }

    /**
     * 두 Money 객체의 금액을 더합니다.
     *
     * @param money1 첫 번째 Money 객체
     * @param money2 두 번째 Money 객체
     * @return 합계 Money 객체
     */
    public Money add(Money money1, Money money2) {
        BigDecimal sum = BigDecimalUtils.is(money1.getValue()).add(money2.getValue());
        return Money.valueOf(sum);
    }

    /**
     * money1에서 money2를 뺍니다.
     *
     * @param money1 첫 번째 Money 객체
     * @param money2 두 번째 Money 객체
     * @return 차이 Money 객체
     */
    public Money subtract(Money money1, Money money2) {
        BigDecimal difference = BigDecimalUtils.is(money1.getValue()).subtract(money2.getValue());
        return Money.valueOf(difference);
    }

    /**
     * Money의 금액을 multiplier로 곱합니다.
     *
     * @param money      Money 객체
     * @param multiplier 곱할 값
     * @return 곱셈 결과 Money 객체
     */
    public Money multiply(Money money, BigDecimal multiplier) {
        BigDecimal product = BigDecimalUtils.is(money.getValue()).multiply(multiplier);
        return Money.valueOf(product);
    }

    /**
     * Money의 금액을 divisor로 나눕니다.
     *
     * @param money     Money 객체
     * @param divisor   나눌 값
     * @param scale     소수점 자리수
     * @param roundingMode 반올림 모드
     * @return 나눗셈 결과 Money 객체
     */
    public Money divide(Money money, BigDecimal divisor, int scale, RoundingMode roundingMode) {
        BigDecimal quotient = BigDecimalUtils.is(money.getValue()).divide(divisor, scale, roundingMode);
        return Money.valueOf(quotient);
    }
}
