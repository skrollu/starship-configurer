package org.starship.configurer.domain.model.components;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.starship.configurer.domain.model.ComponentItem;
import org.starship.configurer.domain.model.ComponentType;

@Data
@SuperBuilder
public class Shield extends ComponentItem {

    @NonNull
    private ShieldPosition position;

    @Override
    public ComponentType getComponentType() {
        return ComponentType.SHIELD;
    }
}