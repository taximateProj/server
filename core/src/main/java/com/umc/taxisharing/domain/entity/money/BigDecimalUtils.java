package com.umc.taxisharing.domain.entity.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtils {


    /**
     * BigDecimal 객체를 래핑하여 추가 기능을 제공하는 메서드.
     *
     * @param source 비교할 BigDecimal 객체
     * @return BigDecimalWrapper 객체
     */
    public static BigDecimalWrapper is(BigDecimal source) {
        return new BigDecimalWrapper(source);
    }

    /**
     * BigDecimal의 래퍼 클래스, 다양한 비교 및 연산 기능 제공.
     */
    public static class BigDecimalWrapper {
        private final BigDecimal source;

        /**
         * BigDecimalWrapper 생성자.
         *
         * @param source 래핑할 BigDecimal 객체
         */
        private BigDecimalWrapper(BigDecimal source) {
            this.source = source;
        }

        /**
         * 두 BigDecimal 객체가 동일한지 비교합니다.
         *
         * @param target 비교할 대상 BigDecimal 객체
         * @return 두 객체가 같으면 true, 그렇지 않으면 false
         */
        public boolean equalTo(BigDecimal target) {
            return this.source.compareTo(target) == 0;
        }

        /**
         * 두 BigDecimal 객체의 크기를 비교하여 현재 객체가 더 큰지 확인합니다.
         *
         * @param target 비교할 대상 BigDecimal 객체
         * @return 현재 객체가 더 크면 true, 그렇지 않으면 false
         */
        public boolean greaterThan(BigDecimal target) {
            return this.source.compareTo(target) > 0;
        }

        /**
         * 두 BigDecimal 객체의 크기를 비교하여 현재 객체가 더 작은지 확인합니다.
         *
         * @param target 비교할 대상 BigDecimal 객체
         * @return 현재 객체가 더 작으면 true, 그렇지 않으면 false
         */
        public boolean lessThan(BigDecimal target) {
            return this.source.compareTo(target) < 0;
        }

        /**
         * 두 BigDecimal 객체의 크기를 비교하여 현재 객체가 크거나 같은지 확인합니다.
         *
         * @param target 비교할 대상 BigDecimal 객체
         * @return 현재 객체가 크거나 같으면 true, 그렇지 않으면 false
         */
        public boolean greaterThanOrEqualTo(BigDecimal target) {
            return this.source.compareTo(target) >= 0;
        }

        /**
         * 두 BigDecimal 객체의 크기를 비교하여 현재 객체가 작거나 같은지 확인합니다.
         *
         * @param target 비교할 대상 BigDecimal 객체
         * @return 현재 객체가 작거나 같으면 true, 그렇지 않으면 false
         */
        public boolean lessThanOrEqualTo(BigDecimal target) {
            return this.source.compareTo(target) <= 0;
        }

        /**
         * BigDecimal 객체에 다른 값을 더하여 결과를 반환합니다.
         *
         * @param augend 더할 BigDecimal 객체
         * @return 합계 BigDecimal 객체
         */
        public BigDecimal add(BigDecimal augend) {
            return this.source.add(augend);
        }

        /**
         * BigDecimal 객체에서 다른 값을 빼서 결과를 반환합니다.
         *
         * @param subtrahend 뺄 BigDecimal 객체
         * @return 차이 BigDecimal 객체
         */
        public BigDecimal subtract(BigDecimal subtrahend) {
            return this.source.subtract(subtrahend);
        }

        /**
         * BigDecimal 객체에 다른 값을 곱하여 결과를 반환합니다.
         *
         * @param multiplicand 곱할 BigDecimal 객체
         * @return 곱셈 결과 BigDecimal 객체
         */
        public BigDecimal multiply(BigDecimal multiplicand) {
            return this.source.multiply(multiplicand);
        }

        /**
         * BigDecimal 객체를 다른 값으로 나눠 결과를 반환합니다.
         *
         * @param divisor 나눌 BigDecimal 객체
         * @param scale 소수점 자리수
         * @param roundingMode 반올림 모드
         * @return 나눗셈 결과 BigDecimal 객체
         */
        public BigDecimal divide(BigDecimal divisor, int scale, RoundingMode roundingMode) {
            return this.source.divide(divisor, scale, roundingMode);
        }

        /**
         * BigDecimal 객체를 주어진 소수점 자리수로 반올림합니다.
         *
         * @param scale 소수점 자리수
         * @param roundingMode 반올림 모드
         * @return 반올림된 BigDecimal 객체
         */
        public BigDecimal round(int scale, RoundingMode roundingMode) {
            return this.source.setScale(scale, roundingMode);
        }

        /**
         * BigDecimal 객체를 소수점 자리수로 포맷팅하여 문자열로 반환합니다.
         *
         * @param scale 소수점 자리수
         * @return 포맷팅된 문자열
         */
        public String format(int scale) {
            return this.source.setScale(scale, RoundingMode.HALF_UP).toPlainString();
        }
    }
}
