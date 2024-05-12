package org.starship.configurer.domain.model.components;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.starship.configurer.domain.model.Component;
import org.starship.configurer.domain.model.ComponentType;


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
