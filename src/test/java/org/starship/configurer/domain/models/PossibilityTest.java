package org.starship.configurer.domain.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PossibilityTest {

    @Test
    void isCompatible_withADifferentComponentType_givesFalse(){
        Possibility instance = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.ONE)
                .build();
        boolean result = instance.isCompatible(ComponentType.HYPERDRIVE, Size.ONE);
        assertThat(result).isFalse();
    }

    @Test
    void isCompatible_withTheSameComponentTypesAndSize_givesTrue(){
        Possibility instance = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.ONE)
                .build();
        boolean result = instance.isCompatible(ComponentType.ENGINE, Size.ONE);
        assertThat(result).isTrue();
    }

    @Test
    void isCompatible_withAGreaterSize_givesFalse(){
        Possibility instance = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.ONE)
                .build();
        boolean result = instance.isCompatible(ComponentType.ENGINE, Size.THREE);
        assertThat(result).isFalse();
    }

    @Test
    void isCompatible_withASmallerSize_givesTrue(){
        Possibility instance = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.THREE)
                .build();
        boolean result = instance.isCompatible(ComponentType.ENGINE, Size.ONE);
        assertThat(result).isTrue();
    }

    @Test
    void isCompatible_withTheSameSize_givesTrue(){
        Possibility instance = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.THREE)
                .build();
        boolean result = instance.isCompatible(ComponentType.ENGINE, Size.THREE);
        assertThat(result).isTrue();
    }
}
