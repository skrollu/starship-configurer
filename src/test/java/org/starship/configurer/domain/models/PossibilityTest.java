package org.starship.configurer.domain.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PossibilityTest {

    @Test
    void isCompatibleWith_withADifferentComponentType_givesFalse(){
        Possibility instance = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.ONE)
                .build();
        boolean result = instance.isCompatibleWith(ComponentType.HYPERDRIVE, Size.ONE, 1);
        assertThat(result).isFalse();
    }

    @Test
    void isCompatibleWith_withTheExactSameComponent_givesTrue(){
        Possibility instance = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.ONE)
                .number(1)
                .build();
        boolean result = instance.isCompatibleWith(ComponentType.ENGINE, Size.ONE, 1);
        assertThat(result).isTrue();
    }

    @Test
    void isCompatibleWith_withAGreaterSize_givesFalse(){
        Possibility instance = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.ONE)
                .build();
        boolean result = instance.isCompatibleWith(ComponentType.ENGINE, Size.THREE, 1);
        assertThat(result).isFalse();
    }

    @Test
    void isCompatibleWith_withASmallerSize_givesTrue(){
        Possibility instance = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.THREE)
                .build();
        boolean result = instance.isCompatibleWith(ComponentType.ENGINE, Size.ONE, 1);
        assertThat(result).isTrue();
    }

    @Test
    void isCompatibleWith_withTheSameSize_givesTrue(){
        Possibility instance = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.THREE)
                .build();
        boolean result = instance.isCompatibleWith(ComponentType.ENGINE, Size.THREE, 1);
        assertThat(result).isTrue();
    }
}
