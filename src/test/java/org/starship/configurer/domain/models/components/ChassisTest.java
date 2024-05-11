package org.starship.configurer.domain.models.components;

import org.junit.jupiter.api.Test;
import org.starship.configurer.domain.models.ComponentType;
import org.starship.configurer.domain.models.Possibility;
import org.starship.configurer.domain.models.Size;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ChassisTest {

    @Test
    void addPossibility_theSamePossibilityTwice_doesNotAddIt() {
        Chassis instance = Chassis.builder()
                .name("Chassis")
                .build();
        Possibility pos1 = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.THREE)
                .number(1)
                .build();
        Possibility samePos = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.THREE)
                .number(1)
                .build();
        instance.addPossibility(pos1);
        instance.addPossibility(samePos);

        assertThat(instance.getPossibilities().size()).isOne();
    }

    @Test
    void addPossibility_theSamePossibilityButGreaterNumber_replaceIt() {
        Chassis instance = Chassis.builder()
                .name("Chassis")
                .build();
        Possibility pos1 = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.ONE)
                .number(1)
                .build();
        Possibility replacer = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.ONE)
                .number(2)
                .build();
        instance.addPossibility(pos1);
        instance.addPossibility(replacer);

        assertThat(instance.getPossibilities().size()).isOne();
        assertThat(instance.getPossibilities().stream().findFirst().get().getNumber()).isEqualTo(2);
    }


    @Test
    void addPossibility_theSamePossibilityButSmallerNumber_doesNotAddIt() {
        Chassis instance = Chassis.builder()
                .name("Chassis")
                .build();
        Possibility pos1 = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.ONE)
                .number(2)
                .build();
        Possibility replacer = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.ONE)
                .number(1)
                .build();
        instance.addPossibility(pos1);
        instance.addPossibility(replacer);

        assertThat(instance.getPossibilities().size()).isOne();
        assertThat(instance.getPossibilities().stream().findFirst().get().getNumber()).isEqualTo(2);
    }

    @Test
    void addPossibility_theSamePossibilityButDifferentSize_doesNotAddIt() {
        Chassis instance = Chassis.builder()
                .name("Chassis")
                .build();
        Possibility pos1 = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.ONE)
                .number(2)
                .build();
        Possibility replacer = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.ONE)
                .number(1)
                .build();
        instance.addPossibility(pos1);
        instance.addPossibility(replacer);

        assertThat(instance.getPossibilities().size()).isOne();
        assertThat(instance.getPossibilities().stream().findFirst().get().getNumber()).isEqualTo(2);
    }

    @Test
    void addPossibility_givenDifferentComponentTypePossibility_addIt() {
        Chassis instance = Chassis.builder()
                .name("Chassis")
                .build();
        Possibility pos1 = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.ONE)
                .number(2)
                .build();
        Possibility pos2 = Possibility.builder()
                .componentType(ComponentType.HYPERDRIVE)
                .size(Size.ONE)
                .number(1)
                .build();
        instance.getPossibilities().add(pos1);

        instance.addPossibility(pos2);

        assertThat(instance.getPossibilities().size()).isEqualTo(2);
    }

    @Test
    void anyGreaterOrEqualSizeWithGreaterOrEqualsNumberPossibility_withGreaterPossibility_givesTrue() {
        Chassis instance = Chassis.builder()
                .name("Chassis")
                .build();
        Possibility pos = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.THREE)
                .number(2)
                .build();
        Possibility toAdd = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.ONE)
                .number(1)
                .build();
        instance.getPossibilities().add(pos);

        boolean result = instance.anyGreaterOrEqualSizeWithGreaterOrEqualsNumberPossibility(toAdd);

        assertThat(result).isTrue();
    }


    @Test
    void anyGreaterOrEqualSizeWithGreaterOrEqualsNumberPossibility_givenSmallerSizeAndSameNumber_givesTrue() {
        Chassis instance = Chassis.builder()
                .name("Chassis")
                .build();
        Possibility pos = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.THREE)
                .number(1)
                .build();
        Possibility toAdd = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.ONE)
                .number(1)
                .build();
        instance.getPossibilities().add(pos);

        boolean result = instance.anyGreaterOrEqualSizeWithGreaterOrEqualsNumberPossibility(toAdd);

        assertThat(result).isTrue();
    }

    @Test
    void anyGreaterOrEqualSizeWithGreaterOrEqualsNumberPossibility_givenSmallerSizeAndNumber_givesFalse() {
        Chassis instance = Chassis.builder()
                .name("Chassis")
                .build();
        Possibility pos = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.ONE)
                .number(1)
                .build();
        Possibility toAdd = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.THREE)
                .number(2)
                .build();
        instance.getPossibilities().add(pos);

        boolean result = instance.anyGreaterOrEqualSizeWithGreaterOrEqualsNumberPossibility(toAdd);

        assertThat(result).isFalse();
    }

    @Test
    void anyGreaterOrEqualSizeWithGreaterOrEqualsNumberPossibility_givenSamePossibility_givesTrue() {
        Chassis instance = Chassis.builder()
                .name("Chassis")
                .build();
        Possibility pos = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.ONE)
                .number(1)
                .build();
        Possibility toAdd = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.ONE)
                .number(1)
                .build();
        instance.getPossibilities().add(pos);

        boolean result = instance.anyGreaterOrEqualSizeWithGreaterOrEqualsNumberPossibility(toAdd);

        assertThat(result).isTrue();
    }


    @Test
    void removeSmallerOrEqualSizeWithSmallerNumberPossibility_whenThereIsGreaterPossibility_doesNotRemoveIt() {
        Chassis instance = Chassis.builder()
                .name("Chassis")
                .build();
        Possibility pos = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.THREE)
                .number(1)
                .build();
        Possibility toAdd = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.ONE)
                .number(2)
                .build();
        instance.getPossibilities().add(pos);

        instance.removeSmallerOrEqualSizeWithSmallerNumberPossibility(toAdd);

        assertThat(instance.getPossibilities().size()).isOne();
        assertThat(instance.getPossibilities().contains(pos)).isTrue();
    }

    @Test
    void removeSmallerOrEqualSizeWithSmallerNumberPossibility_whenThereIsSmallerPossibility_removesIt() {
        Chassis instance = Chassis.builder()
                .name("Chassis")
                .build();
        Possibility pos = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.THREE)
                .number(1)
                .build();
        Possibility toAdd = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.THREE)
                .number(2)
                .build();
        instance.getPossibilities().add(pos);

        instance.removeSmallerOrEqualSizeWithSmallerNumberPossibility(toAdd);

        assertThat(instance.getPossibilities().size()).isZero();
    }

    @Test
    void removeSmallerOrEqualSizeWithSmallerNumberPossibility_whenSamePossibility_doNothing() {
        Chassis instance = Chassis.builder()
                .name("Chassis")
                .build();
        Possibility pos = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.THREE)
                .number(1)
                .build();
        Possibility toAdd = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.THREE)
                .number(1)
                .build();
        instance.getPossibilities().add(pos);

        instance.removeSmallerOrEqualSizeWithSmallerNumberPossibility(toAdd);

        assertThat(instance.getPossibilities().size()).isOne();
        assertThat(instance.getPossibilities().contains(pos)).isTrue();
        // Because of same hashcode
        assertThat(instance.getPossibilities().contains(toAdd)).isTrue();
    }

    @Test
    void howManyCompatibleComponentAllowed_givenACompatibleComponent_givesHowManyComponentsAreAllowed() {
        Chassis instance = Chassis.builder()
                .name("Chassis")
                .build();
        Possibility pos = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.THREE)
                .number(1)
                .build();
        Possibility pos2 = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.TWO)
                .number(3)
                .build();
        instance.getPossibilities().add(pos);
        instance.getPossibilities().add(pos2);

        int result = instance.howManyCompatibleComponentAllowed(Engine.builder()
                .name("Engine")
                .size(Size.ONE)
                .build(), 1);

        assertThat(result).isEqualTo(3);
    }

    @Test
    void howManyCompatibleComponentAllowed_givenANotCompatibleComponent_givesZero() {
        Chassis instance = Chassis.builder()
                .name("Chassis")
                .build();
        Possibility pos = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.ONE)
                .number(1)
                .build();
        Possibility pos2 = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.TWO)
                .number(3)
                .build();
        instance.getPossibilities().add(pos);
        instance.getPossibilities().add(pos2);

        int result = instance.howManyCompatibleComponentAllowed(Engine.builder()
                .name("Engine")
                .size(Size.THREE)
                .build(), 1);

        assertThat(result).isZero();
    }

    @Test
    void howManyCompatibleComponentAllowed_givenADifferentComponent_givesZero() {
        Chassis instance = Chassis.builder()
                .name("Chassis")
                .build();
        Possibility pos = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.ONE)
                .number(1)
                .build();
        Possibility pos2 = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.TWO)
                .number(3)
                .build();
        instance.getPossibilities().add(pos);
        instance.getPossibilities().add(pos2);

        int result = instance.howManyCompatibleComponentAllowed(Hyperdrive.builder()
                .name("Hyperdrive")
                .size(Size.THREE)
                .build(), 1);

        assertThat(result).isZero();
    }

    @Test
    void getAllCompatiblePossibility_givenACompatibleComponent_givesCompatibilities() {
        Chassis instance = Chassis.builder()
                .name("Chassis")
                .build();
        Possibility pos = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.THREE)
                .number(1)
                .build();
        Possibility pos1 = Possibility.builder()
                .componentType(ComponentType.ENGINE)
                .size(Size.TWO)
                .number(2)
                .build();
        instance.getPossibilities().add(pos);
        instance.getPossibilities().add(pos1);

        List<Possibility> result = instance.getAllCompatiblePossibility(Engine.builder()
                .name("Engine")
                .build(), 1);

        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void getAllCompatiblePossibility_givenANotCompatibleComponent_givesNothing() {
        Chassis instance = Chassis.builder()
                .name("Chassis")
                .build();
        Possibility pos = Possibility.builder()
                .componentType(ComponentType.HYPERDRIVE)
                .size(Size.THREE)
                .number(1)
                .build();
        instance.getPossibilities().add(pos);

        List<Possibility> result = instance.getAllCompatiblePossibility(Engine.builder()
                .name("Engine")
                .build(), 1);

        assertThat(result.size()).isZero();
    }
}
