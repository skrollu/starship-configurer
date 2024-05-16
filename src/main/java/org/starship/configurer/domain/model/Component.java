package org.starship.configurer.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.starship.configurer.domain.model.components.Chassis;

import java.util.UUID;

@SuperBuilder
@Data
public abstract class Component {
    @NonNull
    private UUID id;
    @NonNull
    private String name;
    private String manufacturer;
    private double weight;
    @NonNull
    @Builder.Default
    private Size size = Size.ONE;

    public abstract ComponentType getComponentType();

    public boolean isChassis() {
        return this instanceof Chassis;
    }
}
