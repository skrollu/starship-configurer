package org.starship.configurer.domain.models.components;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.starship.configurer.domain.models.Component;
import org.starship.configurer.domain.models.ComponentType;

@Data
@SuperBuilder
public class Weapon extends Component {

    @NonNull
    @Builder.Default
    private Long thrustPower = 1L; // Newtons in N
    private WeaponType type;
    private Long firingFrequency; // fire per minute
    @Builder.Default
    private boolean xAxisRotation = false;
    @Builder.Default
    private boolean yAxisRotation = false;
    @Builder.Default
    private boolean zAxisRotation = false;

    @Override
    public ComponentType getComponentType() {
        return ComponentType.WEAPON;
    }
}