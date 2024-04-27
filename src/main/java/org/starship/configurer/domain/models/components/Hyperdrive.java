package org.starship.configurer.domain.models.components;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.starship.configurer.domain.models.Component;
import org.starship.configurer.domain.models.ComponentType;

@Data
@SuperBuilder
public class Hyperdrive extends Component {

    @NonNull
    @Builder.Default
    private Long maxSpeed = 1L; // in Km/h

    @Override
    public ComponentType getComponentType() {
        return ComponentType.HYPERDRIVE;
    }
}
