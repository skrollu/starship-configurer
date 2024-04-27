package org.starship.configurer.domain.models.components;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.starship.configurer.domain.models.Component;
import org.starship.configurer.domain.models.ComponentType;

@Data
@SuperBuilder
public class Reactor extends Component {
    @NonNull
    @Builder.Default
    private Long thrustPower = 1L; // Newtons in N

    @Override
    public ComponentType getComponentType() {
        return ComponentType.REACTOR;
    }
}