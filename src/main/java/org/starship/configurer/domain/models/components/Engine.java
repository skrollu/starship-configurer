package org.starship.configurer.domain.models.components;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.starship.configurer.domain.models.Component;
import org.starship.configurer.domain.models.ComponentType;

@Data
@SuperBuilder
public class Engine extends Component {

    @NonNull
    private Long thrustPower;

    @Override
    public ComponentType getComponentType() {
        return ComponentType.ENGINE;
    }
}
