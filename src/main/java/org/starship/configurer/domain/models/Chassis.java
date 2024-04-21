package org.starship.configurer.domain.models;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@SuperBuilder
@Data
public class Chassis extends Component {
    private Set<Possibility> possibilities;
    private ChassisType type;
}
