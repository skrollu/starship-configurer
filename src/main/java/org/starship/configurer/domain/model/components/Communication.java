package org.starship.configurer.domain.model.components;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.starship.configurer.domain.model.Component;
import org.starship.configurer.domain.model.ComponentType;

@Data
@SuperBuilder
public class Communication extends Component {
    @NonNull
    @Builder.Default
    private Long range = 1L; // in km
    @NonNull
    @Builder.Default
    private Long frequencyBandWidth = 1000L; // in Hertz (Hz)
    @Builder.Default
    private int channels = 10;

    @Override
    public ComponentType getComponentType() {
        return ComponentType.COMMUNICATION;
    }
}