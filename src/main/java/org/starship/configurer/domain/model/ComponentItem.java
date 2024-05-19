package org.starship.configurer.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.starship.configurer.domain.model.components.Chassis;

import java.util.UUID;

@SuperBuilder
@Data
public class ComponentItem implements Component {
    @NonNull
    private UUID id;
    @NonNull
    private String name;
    private String manufacturer;
    private double weight;
    @NonNull
    @Builder.Default
    private Size size = Size.ONE;

    /**
     * Must be overridden
     */
    @Override
    public ComponentType getComponentType() {
        return null;
    }

    public boolean isChassis() {
        return this instanceof Chassis;
    }
}
