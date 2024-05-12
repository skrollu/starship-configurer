package org.starship.configurer.domain.model.components;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.starship.configurer.domain.model.Component;
import org.starship.configurer.domain.model.ComponentType;

@Data
@SuperBuilder
public class Engine extends Component {

    @NonNull
    @Builder.Default
    private Long thrustPower = 1L; // Newtons in N

    @Override
    public ComponentType getComponentType() {
        return ComponentType.ENGINE;
    }
}
