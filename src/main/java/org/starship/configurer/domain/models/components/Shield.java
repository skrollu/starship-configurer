package org.starship.configurer.domain.models.components;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.starship.configurer.domain.models.Component;
import org.starship.configurer.domain.models.ComponentType;

@Data
@SuperBuilder
public class Shield extends Component {

    @NonNull
    private ShieldPosition position;

    @Override
    public ComponentType getComponentType() {
        return ComponentType.SHIELD;
    }
}