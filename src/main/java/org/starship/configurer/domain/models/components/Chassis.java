package org.starship.configurer.domain.models.components;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import lombok.extern.jbosslog.JBossLog;
import org.jboss.logging.annotations.Pos;
import org.starship.configurer.domain.models.Component;
import org.starship.configurer.domain.models.ComponentType;
import org.starship.configurer.domain.models.Possibility;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@JBossLog
@Data
@SuperBuilder
public class Chassis extends Component {
    @NonNull
    @Builder.Default
    List<Possibility> possibilities = new ArrayList<>();

    @Override
    public ComponentType getComponentType() {
        return ComponentType.CHASSIS;
    }

    public Possibility getCompatiblePossibility(Component component) {
        Optional<Possibility> opt = this.possibilities.stream()
                .filter(c -> c.getComponentType().equals(component.getComponentType()))
                .filter(c -> c.getSize().equals(component.getSize()))
                .findFirst();
        if (!opt.isPresent()) {
            log.warnv("The given Component is not compatible {0}", component);
            return null;
        }
        return opt.get();
    }

    public int howManyCompatibleComponentAllowed(Component component) {
        Possibility possibility = getCompatiblePossibility(component);
        if (Objects.isNull(possibility)) {
            return 0;
        }
        return possibility.getNumber();
    }
}
