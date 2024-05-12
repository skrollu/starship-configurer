package org.starship.configurer.domain.model;

import org.junit.jupiter.api.Test;
import org.starship.configurer.domain.model.components.Chassis;
import org.starship.configurer.domain.model.components.Engine;
import org.starship.configurer.domain.model.components.Hyperdrive;
import org.starship.configurer.domain.model.exceptions.DifferentComponentTypeException;
import org.starship.configurer.domain.model.exceptions.NotConfiguredComponentException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.starship.configurer.domain.model.ComponentType.CHASSIS;
import static org.starship.configurer.domain.model.ComponentType.HYPERDRIVE;

public class StarshipConfigurationTest {

    @Test
    void build_aMinimalConfiguration_hasDefaultValue() {
        StarshipConfiguration instance = StarshipConfiguration.builder()
                .name("Config")
                .build();

        assertThat(instance.getName()).isEqualTo("Config");
        assertThat(instance.getConfiguration()).isEmpty();
        assertThat(instance.getCreatedAt()).isBefore(LocalDateTime.MAX);
        assertThat(instance.getStatus()).isEqualTo(StarshipConfigurationStatus.DRAFT);
    }

    @Test
    void addComponent_addFirstChassis_works() {
        Chassis chassis = Chassis.builder()
                .name("Chassis")
                .build();
        StarshipConfiguration instance = StarshipConfiguration.builder()
                .name("Config")
                .build();

        instance.addComponent(chassis, 1);

        assertThat(instance.getChassis().getName()).isEqualTo("Chassis");
    }

    @Test
    void addComponent_addSecondChassis_replaceTheFirstOne() {
        Chassis chassis = Chassis.builder()
                .name("Chassis")
                .build();
        Chassis chassis2 = Chassis.builder()
                .name("Chassis2")
                .build();
        StarshipConfiguration instance = StarshipConfiguration.builder()
                .name("Config")
                .build();

        instance.addComponent(chassis, 1);
        instance.addComponent(chassis2, 1);

        assertThat(instance.getChassis().getName()).isEqualTo("Chassis2");
    }

    /**
     * addNotCompatibleComponent
     * addCompatibleComponent
     * addMultipleSameComponent
     * <p>
     * ~ howManyCompatibleComponentAllowed
     * X getChassis
     * X replaceComponent
     * X removeComponent
     * X clearConfiguration
     */

// TODO test this when compatibility feature will be done
//    @Test
//    void addComponent_addNotCompatibleComponent_() {
//        Chassis chassis = Chassis.builder()
//                .name("Chassis")
//                .build();
//        Chassis chassis2 = Chassis.builder()
//                .name("Chassis2")
//                .build();
//        StarshipConfiguration instance = StarshipConfiguration.builder()
//                .name("Config")
//                .build();
//
//        instance.addComponent(chassis);
//        instance.addComponent(chassis2);
//
//        assertThat(instance.getChassis().getName()).isEqualTo("Chassis2");
//    }
    @Test
    void howManyCompatibleComponentAllowed_whenChassisIsNotConfigured_givesZero() {
        StarshipConfiguration instance = StarshipConfiguration.builder()
                .name("Config")
                .build();
        Engine engine = Engine.builder()
                .name("Engine")
                .thrustPower(100L)
                .build();

        int result = instance.howManyCompatibleComponentAllowed(engine);

        assertThat(result).isZero();
    }

    // TODO search how to mock this...
//    @Test
//    void howManyCompatibleComponentAllowed_whenChassisIsNotConfigured_chassisCompatibility() {
//        StarshipConfiguration instance = StarshipConfiguration.builder()
//                .name("Config")
//                .build();
//        Chassis chassis = Chassis.builder()
//                .name("Chassis")
//                .build();
//        Chassis chassis = mock(Chassis.class);
//        Engine engine = Engine.builder()
//                .name("Engine")
//                .thrustPower(100L)
//                .build();
//        instance.getComponents().add(chassis);
//        System.out.println(instance.getComponents());
//        when(chassis.howManyCompatibleComponentAllowed(engine))
//                .thenReturn(1);
//
//        int result = instance.howManyCompatibleComponentAllowed(engine);
//
//        assertThat(result).isOne();
//    }

    @Test
    void getChassis_whenChassisIsNotConfigured_givesNothing() {
        StarshipConfiguration instance = StarshipConfiguration.builder()
                .name("Config")
                .build();

        Chassis result = instance.getChassis();

        assertThat(result).isNull();
    }

    @Test
    void getChassis_whenChassisIsConfigured_givesNothing() {
        StarshipConfiguration instance = StarshipConfiguration.builder()
                .name("Config")
                .build();
        Chassis chassis = Chassis.builder()
                .name("Chassis")
                .build();
        instance.getConfiguration().put(CHASSIS, List.of(chassis));

        Chassis result = instance.getChassis();

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Chassis");
    }


    @Test
    void replaceComponent_differentTypesOfComponent_throwsError() {
        StarshipConfiguration instance = StarshipConfiguration.builder()
                .name("Config")
                .build();
        Chassis chassis = Chassis.builder()
                .name("Chassis")
                .build();
        Hyperdrive hyperdrive = Hyperdrive.builder()
                .name("HD")
                .maxSpeed(100L)
                .build();
        Engine engine = Engine.builder()
                .name("Engine")
                .thrustPower(100L)
                .build();
        instance.getConfiguration().put(CHASSIS, List.of(chassis));
        instance.getConfiguration().put(HYPERDRIVE, List.of(hyperdrive));

        assertThrows(DifferentComponentTypeException.class, () ->
                instance.replaceComponent(hyperdrive, engine));
    }

    @Test
    void replaceComponent_works() {
        StarshipConfiguration instance = StarshipConfiguration.builder()
                .name("Config")
                .build();
        Possibility possibility = Possibility.builder()
                .number(1)
                .size(Size.THREE)
                .componentType(HYPERDRIVE)
                .build();
        Chassis chassis = Chassis.builder()
                .name("Chassis")
                .possibilities(Set.of(possibility))
                .build();
        Hyperdrive hyperdrive = Hyperdrive.builder()
                .name("HD")
                .maxSpeed(100L)
                .build();
        Hyperdrive hyperdrive1 = Hyperdrive.builder()
                .name("hyperdrive1")
                .maxSpeed(1000L)
                .build();
        instance.getConfiguration().put(CHASSIS, List.of(chassis));
        instance.getConfiguration().put(HYPERDRIVE, List.of(hyperdrive));

        instance.replaceComponent(hyperdrive, hyperdrive1);

        assertThat(instance.getConfiguration().get(HYPERDRIVE).contains(hyperdrive1)).isTrue();
        assertThat(instance.getConfiguration().get(HYPERDRIVE).contains(hyperdrive)).isFalse();
    }

    @Test
    void removeComponent_removeAChassis_clearsConfiguration() {
        StarshipConfiguration instance = StarshipConfiguration.builder()
                .name("Config")
                .build();
        Chassis chassis = Chassis.builder()
                .name("Chassis")
                .build();
        Hyperdrive hyperdrive = Hyperdrive.builder()
                .name("HD")
                .maxSpeed(100L)
                .build();
        Hyperdrive hyperdrive1 = Hyperdrive.builder()
                .name("HD")
                .maxSpeed(1000L)
                .build();
        instance.getConfiguration().put(CHASSIS, List.of(chassis));
        instance.getConfiguration().put(HYPERDRIVE, List.of(hyperdrive, hyperdrive1));

        instance.removeComponent(chassis);

        assertThat(instance.getConfiguration()).isEmpty();
    }

    @Test
    void removeComponent_removePresentComponent_removesIt() {
        StarshipConfiguration instance = StarshipConfiguration.builder()
                .name("Config")
                .build();
        Chassis chassis = Chassis.builder()
                .name("Chassis")
                .build();
        Hyperdrive hyperdrive = Hyperdrive.builder()
                .name("HD")
                .maxSpeed(100L)
                .build();
        Hyperdrive hyperdrive1 = Hyperdrive.builder()
                .name("HD")
                .maxSpeed(1000L)
                .build();

        instance.getConfiguration().put(CHASSIS, List.of(chassis));
        instance.getConfiguration().put(HYPERDRIVE, List.of(hyperdrive, hyperdrive1));

        instance.removeComponent(hyperdrive1);

        assertThat(instance.getConfiguration().get(HYPERDRIVE).contains(hyperdrive)).isTrue();
        assertThat(instance.getConfiguration().get(HYPERDRIVE).contains(hyperdrive1)).isFalse();
    }

    @Test
    void removeComponent_removeNotPresentComponent_throwsError() {
        StarshipConfiguration instance = StarshipConfiguration.builder()
                .name("Config")
                .build();
        Chassis chassis = Chassis.builder()
                .name("Chassis")
                .build();
        Hyperdrive hyperdrive = Hyperdrive.builder()
                .name("HD")
                .maxSpeed(100L)
                .build();
        Hyperdrive notAddedComponent = Hyperdrive.builder()
                .name("notAddedComponent")
                .maxSpeed(1000L)
                .build();

        instance.getConfiguration().put(CHASSIS, List.of(chassis));
        instance.getConfiguration().put(HYPERDRIVE, List.of(hyperdrive));

        assertThrows(NotConfiguredComponentException.class, () -> instance.removeComponent(notAddedComponent));
    }

    @Test
    void clearConfiguration_removesAllComponents() {
        StarshipConfiguration instance = StarshipConfiguration.builder()
                .name("Config")
                .build();
        Chassis chassis = Chassis.builder()
                .name("Chassis")
                .build();
        Hyperdrive hyperdrive = Hyperdrive.builder()
                .name("HD")
                .maxSpeed(100L)
                .build();
        Hyperdrive hyperdrive1 = Hyperdrive.builder()
                .name("HD")
                .maxSpeed(100L)
                .build();

        instance.getConfiguration().put(CHASSIS, List.of(chassis));
        instance.getConfiguration().put(HYPERDRIVE, List.of(hyperdrive, hyperdrive1));

        instance.clearConfiguration();
        assertThat(instance.getConfiguration()).isEmpty();
    }
}
