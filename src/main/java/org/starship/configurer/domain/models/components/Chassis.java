package org.starship.configurer.domain.models.components;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import lombok.extern.jbosslog.JBossLog;
import org.starship.configurer.domain.models.Component;
import org.starship.configurer.domain.models.ComponentType;
import org.starship.configurer.domain.models.Possibility;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@JBossLog
@Data
@SuperBuilder
public class Chassis extends Component {

    @NonNull
    @Builder.Default
    Set<Possibility> possibilities = new HashSet<>();

    @Override
    public ComponentType getComponentType() {
        return ComponentType.CHASSIS;
    }

    /**
     * Add the given possibility to the possibilities.
     *
     * @param possibility
     */
    public void addPossibility(final @NonNull Possibility possibility) {
        // handle different componentType
        if (getSameComponentTypePossibilities(possibility).isEmpty()) {
            this.possibilities.add(possibility);
            return;
        }
        // handle same componentType
        // if any greater Possibility configured with greater number, don't add the given possibility
        if (this.anyGreaterOrEqualSizeWithGreaterOrEqualsNumberPossibility(possibility))
            return;
        this.removeSmallerOrEqualSizeWithSmallerNumberPossibility(possibility);
        this.possibilities.add(possibility);
    }

    /**
     * @param possibility
     * @return true if there is any greater possibility than the given one
     */
    boolean anyGreaterOrEqualSizeWithGreaterOrEqualsNumberPossibility(final @NonNull Possibility possibility) {
        List<Possibility> sameComponentPossibilities = this.getSameComponentTypePossibilities(possibility);
        if (sameComponentPossibilities.isEmpty())
            return false;
        Optional<Possibility> anyGreaterSizeWithGreaterNumber = sameComponentPossibilities.stream()
                .filter(p -> p.getSize().isGreaterThanOrEqualTo(possibility.getSize()))
                .filter(p -> p.getNumber() >= possibility.getNumber())
                .findAny();
        log.debugv("#anyGreaterSizeWithGreaterNumberPossibility - result {0}", anyGreaterSizeWithGreaterNumber.isPresent());
        return anyGreaterSizeWithGreaterNumber.isPresent();
    }

    /**
     * Removes possibilities with:
     * <br/> -   Size smaller or equal to the given possibility
     * <br/> -   and strictly smaller number to the given possibility
     *
     * @param possibility
     */
    void removeSmallerOrEqualSizeWithSmallerNumberPossibility(final @NonNull Possibility possibility) {
        List<Possibility> sameComponentTypePossibilities = this.getSameComponentTypePossibilities(possibility);
        if (sameComponentTypePossibilities.isEmpty())
            return;
        List<Possibility> inferiorPossibilityToRemove = sameComponentTypePossibilities.stream()
                .filter(p -> p.getSize().isSmallerThanOrEqualTo(possibility.getSize()))
                .filter(p -> p.getNumber() < possibility.getNumber())
                .collect(Collectors.toList());

        this.possibilities.removeAll(inferiorPossibilityToRemove);
        log.infov("#removeSmallerSizeWithSmallerNumberPossibility - Removed {0}", inferiorPossibilityToRemove);
    }

    private List<Possibility> getSameComponentTypePossibilities(final @NonNull Possibility possibility) {
        return this.possibilities.stream()
                .filter(p -> p.getComponentType().equals(possibility.getComponentType()))
                .collect(Collectors.toList());
    }

    /**
     * @return 0 if no component are compatible with this chassis
     * </br> the highest number of given component allowed to set in this chassis
     */
    // FIXME remove second param
    public int howManyCompatibleComponentAllowed(final @NonNull Component component, final int numberOfComponent) {
        List<Possibility> compatiblePossibilities = getAllCompatiblePossibility(component, numberOfComponent);
        if (compatiblePossibilities.isEmpty()) {
            return 0;
        }

        return compatiblePossibilities.stream()
                .map(Possibility::getNumber)
                .mapToInt(n -> n)
                .max()
                .orElse(0);
    }

    /**
     * @param component         the component to evaluate the compatibility
     * @param numberOfComponent the number of component to evaluate the compatibility
     * @return all possibility compatible with the given params
     */
    public List<Possibility> getAllCompatiblePossibility(final @NonNull Component component, final int numberOfComponent) {
        Possibility componentPossibility = Possibility.builder()
                .componentType(component.getComponentType())
                .size(component.getSize())
                .number(numberOfComponent)
                .build();

        List<Possibility> result = this.possibilities.stream()
                .filter(p -> p.isCompatibleWith(componentPossibility))
                .collect(Collectors.toList());
        if (result.isEmpty())
            log.warnv("This chassis is not compatible with the given component {0} and number {1}", component, numberOfComponent);
        return result;
    }
}
