package org.starship.configurer.domain.model.components;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.starship.configurer.domain.model.ComponentItem;
import org.starship.configurer.domain.model.ComponentType;


@Data
@SuperBuilder
public class Tank extends ComponentItem {
    @NonNull
    @Builder.Default
    private Long capacity = 1L; // Volume in m3

    @Override
    public ComponentType getComponentType() {
        return ComponentType.TANK;
    }
}
