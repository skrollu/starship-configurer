package org.starship.configurer.domain.model.components;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.starship.configurer.domain.model.Component;
import org.starship.configurer.domain.model.ComponentType;

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