package org.starship.configurer.domain.models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jbosslog.JBossLog;
import org.starship.configurer.domain.models.components.Chassis;
import org.starship.configurer.domain.models.exceptions.DifferentComponentTypeException;
import org.starship.configurer.domain.models.exceptions.NotConfiguredComponentException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Builder
@Data
@JBossLog
public class StarshipConfiguration {
    private UUID id;
    @NonNull
    private String name;
    private double height;
    private double length;
    private double width;
    private double weight;
    private Manufacturer manufacturer;
    @NonNull
    @Builder.Default
    // TODO work on Component hashcode
    private Set<Component> components = new HashSet<>();
    @NonNull
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt; // TODO check value
    @NonNull
    @Builder.Default
    private StarshipConfigurationStatus status = StarshipConfigurationStatus.DRAFT;

    public void addComponent(@NonNull final Component component) {
        boolean isChassisConfigured = isChassisConfigured();
        boolean isComponentAChassis = component instanceof Chassis;

        // handle chassis
        if (!isChassisConfigured && !isComponentAChassis) {
            log.warnv("Chassis must be configured first.");
            return;
        } else if (isChassisConfigured && isComponentAChassis) {
            this.replaceChassis((Chassis) component);
            return;
        } else if (!isChassisConfigured && isComponentAChassis) {
            components.add(component);
            return;
        }

        // handle other components
        int allowed = this.howManyCompatibleComponentAllowed(component);
        Set<Component> sameComponentConfigured = this.getSameComponents(component);
        // TODO manage add more than one component
        if ((sameComponentConfigured.size() + 1) > allowed) {
            log.warnv("There are already to many {0}", component.getComponentType());
            return;
        }
        components.add(component);
    }

    /**
     * @return true if a CHASSIS is in components
     */
    private boolean isChassisConfigured() {
        return this.components.stream()
                .anyMatch(c -> c.isChassis());
    }

    /**
     * Retrieve chassis and check how many given component are compatible
     *
     * @return <br/> 0 if chassis isn't configured
     * <br/> how many compatible components are allowed
     */
    public int howManyCompatibleComponentAllowed(final @NonNull Component component) {
        Chassis chassis = this.getChassis();
        if (Objects.isNull(chassis))
            return 0;
        return chassis.howManyCompatibleComponentAllowed(component);
    }

    /**
     * @return Chassis if configured else null
     */
    public Chassis getChassis() {
        Optional<Chassis> result = this.getComponents().stream()
                .filter(c -> c.isChassis())
                .map(Chassis.class::cast)
                .findFirst();
        if (!result.isPresent()) {
            log.infov("There is no chassis configured.");
            return null;
        }
        return result.get();
    }

    private Set<Component> getSameComponents(Component component) {
        return this.components.stream()
                .filter(c -> c.getComponentType().equals(component.getComponentType()))
                .collect(Collectors.toSet());
    }

    private void replaceChassis(final @NonNull Chassis chassis) {
        this.replaceComponent(this.getChassis(), chassis);
    }

    public void replaceComponent(final @NonNull Component toRemove, final @NonNull Component with) {
        if (!toRemove.getComponentType().equals(with.getComponentType())) {
            throw new DifferentComponentTypeException(String.format(
                    "#replaceComponent - Cannot replace components of different types - toRemove {0} with {1}",
                    toRemove.getComponentType(), with.getComponentType()));
        }
        this.removeComponent(toRemove);
        this.getComponents().add(with);
        log.debugv("#replaceComponent - Replaced {0}  with {1}", toRemove, with);
    }

    /**
     * Remove the given component. Clean the configuration if component is a Chassis.
     *
     * @param toRemove The component to remove
     */
    public void removeComponent(final @NonNull Component toRemove) {
        if (!this.components.contains(toRemove)) {
            throw new NotConfiguredComponentException(String.format("#removeComponent - Cannot remove not configured component - toRemove {0}", toRemove));
        }
        if (toRemove instanceof Chassis)
            this.cleanConfiguration();
        else {
            this.getComponents().remove(toRemove);
            log.debugv("#removeComponent - Removed {0}", toRemove);
        }
    }

    /**
     * Remove all components from the configuration
     */
    public void cleanConfiguration() {
        this.components.removeAll(this.components);
        log.debugv("#cleanConfiguration - Configuration cleaned {0}", this.getComponents());
    }

    /**
     * Publish the configuration
     */
    public void publishConfiguration() {
        this.setStatus(StarshipConfigurationStatus.PUBLISH);
        log.debugv("#publishConfiguration - Configuration status {0}", this.getStatus());
    }
}
