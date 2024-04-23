package org.starship.configurer.domain.models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.starship.configurer.domain.models.components.Chassis;

import java.util.UUID;

@SuperBuilder
@Data
public abstract class Component {
    private UUID id;
    @NonNull
    private String name;
    private Manufacturer manufacturer;
    private double weight;
    @NonNull
    @Builder.Default
    private Size size = Size.ONE;

    public abstract ComponentType getComponentType();

    public boolean isChassis() {
        return this instanceof Chassis;
    }
}
