package org.starship.configurer.domain.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SizeTest {

    @Test
    void compare_aGreaterValue_givesOne() {
        Size instance = Size.TWO;
        int result = instance.compare(Size.ONE);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void compare_theSameValue_givesZero() {
        Size instance = Size.TWO;
        int result = instance.compare(Size.TWO);
        assertThat(result).isEqualTo(0);
    }

    @Test
    void compare_aSmallerValue_givesMinusOne() {
        Size instance = Size.TWO;
        int result = instance.compare(Size.THREE);
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void isGreaterThan_aSmallerValue_givesFalse() {
        Size instance = Size.ONE;
        boolean result = instance.isGreaterThan(Size.TWO);
        assertThat(result).isFalse();
        result = instance.isGreaterThan(Size.THREE);
        assertThat(result).isFalse();
    }

    @Test
    void isGreaterThan_aGreaterValue_givesTrue() {
        Size instance = Size.THREE;
        boolean result = instance.isGreaterThan(Size.TWO);
        assertThat(result).isTrue();
        result = instance.isGreaterThan(Size.ONE);
        assertThat(result).isTrue();
    }

    @Test
    void isGreaterThan_theSameValue_givesFalse() {
        Size instance = Size.THREE;
        boolean result = instance.isGreaterThan(Size.THREE);
        assertThat(result).isFalse();
    }

    @Test
    void isSmallerThan_aSmallerValue_givesFalse() {
        Size instance = Size.THREE;
        boolean result = instance.isSmallerThan(Size.TWO);
        assertThat(result).isFalse();
        result = instance.isSmallerThan(Size.ONE);
        assertThat(result).isFalse();
    }

    @Test
    void isSmallerThan_aGreaterValue_givesTrue() {
        Size instance = Size.ONE;
        boolean result = instance.isSmallerThan(Size.TWO);
        assertThat(result).isTrue();
        result = instance.isSmallerThan(Size.THREE);
        assertThat(result).isTrue();
    }

    @Test
    void isSmallerThan_theSameValue_givesFalse() {
        Size instance = Size.THREE;
        boolean result = instance.isSmallerThan(Size.THREE);
        assertThat(result).isFalse();
    }
}
