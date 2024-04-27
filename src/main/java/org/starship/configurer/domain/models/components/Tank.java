package org.starship.configurer.domain.models.components;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.starship.configurer.domain.models.Component;
import org.starship.configurer.domain.models.ComponentType;


@Data
@SuperBuilder
public class Tank extends Component {
    @NonNull
    @Builder.Default
    private Long capacity = 1L; // Volume in m3

    @Override
    public ComponentType getComponentType() {
        return ComponentType.TANK;
    }
}
