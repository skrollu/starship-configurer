package org.starship.configurer.domain.models;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.starship.configurer.domain.models.components.Chassis;

import java.time.LocalDateTime;

public class StarshipConfigurationTest {

    @Test
    void build_aMinimalConfiguration_hasDefaultValue() {
        StarshipConfiguration instance = StarshipConfiguration.builder()
                .name("Config")
                .build();

        assertThat(instance.getName()).isEqualTo("Config");
        assertThat(instance.getComponents()).isEmpty();
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

        instance.addComponent(chassis);

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

        instance.addComponent(chassis);
        instance.addComponent(chassis2);

        assertThat(instance.getChassis().getName()).isEqualTo("Chassis2");
    }
}
