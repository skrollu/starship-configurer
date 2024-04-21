package org.starship.configurer.domain.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Possibility {
    private ComponentType componentType;
    private int number;
    private Size size;
}
