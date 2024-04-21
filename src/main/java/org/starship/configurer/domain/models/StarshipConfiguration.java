package org.starship.configurer.domain.models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
public class StarshipConfiguration {
    private UUID id;
    @NonNull
    private String name;
    private double height;
    private double length;
    private double width;
    private double weight;
    private Manufacturer manufacturer;
    private Set<Component> components;
    @NonNull
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt; // TODO check value
    @NonNull
    private StarshipConfigurationStatus status;

    private void addComponent(@NonNull final Component component, @NonNull final ComponentType componentType) {

        if (!componentType.equals(ComponentType.CHASSIS)) {
            isChassisConfigured();
        }
    }

    /**
     * @return true if a CHASSIS is in components
     */
    private boolean isChassisConfigured() {
        return Objects.nonNull(this.components.stream().filter(component -> {
                    return component.getComponentType().equals(ComponentType.CHASSIS);
                }).findFirst()
                .get());
    }
}
