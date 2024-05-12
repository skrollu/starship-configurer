package org.starship.configurer.domain.models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jbosslog.JBossLog;
import org.apache.commons.collections4.ListUtils;
import org.starship.configurer.domain.models.components.Chassis;
import org.starship.configurer.domain.models.exceptions.DifferentComponentTypeException;
import org.starship.configurer.domain.models.exceptions.NotConfiguredComponentException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.starship.configurer.domain.models.ComponentType.CHASSIS;

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
    private Map<ComponentType, List<Component>> configuration = new HashMap<>();
    @NonNull
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt; // TODO check value
    @NonNull
    @Builder.Default
    private StarshipConfigurationStatus status = StarshipConfigurationStatus.DRAFT;

    /**
     * Add the given component to the configuration
     *
     * @param component          The component to add to the configuration
     * @param numberOfComponents The number of the given component to add to the configuration, ignored if the component is a chassis
     */
    public void addComponent(@NonNull final Component component, final int numberOfComponents) {
        boolean isChassisConfigured = isChassisConfigured();
        boolean isComponentAChassis = component instanceof Chassis;

        // handle chassis
        if (!isChassisConfigured && !isComponentAChassis) {
            throw new IllegalStateException(String.format("Chassis must be configured first"));
        } else if (isChassisConfigured && isComponentAChassis) { // TODO manage numberOfComponents here
            this.replaceChassis((Chassis) component);
            return;
        } else if (!isChassisConfigured && isComponentAChassis) {
            List<Component> chassis = new ArrayList<>();
            chassis.add(component);
            configuration.put(CHASSIS, chassis);
            return;
        }

        // handle other components
        int allowed = this.howManyCompatibleComponentAllowed(component);
        List<Component> configuredComponent = this.getComponentTypeConfigured(component.getComponentType());
        if ((configuredComponent.size() + numberOfComponents) > allowed) {
            throw new IllegalStateException(String.format("Cannot add {0} components, too many {1} configured", numberOfComponents, component.getComponentType()));
        }
        List<Component> result = new ArrayList<>();
        result.addAll(configuredComponent);
        for (int i = 0; i < numberOfComponents; i++) {
            result.add(component);
        }
        configuration.put(component.getComponentType(), result);
    }

    /**
     * @return true if a CHASSIS is in components
     */
    private boolean isChassisConfigured() {
        return this.configuration.entrySet().stream()
                .filter(e -> e.getKey().equals(CHASSIS))
                .anyMatch(e -> e.getValue().size() > 0);
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
        return chassis.howManyCompatibleComponentAllowed(component, 1);
    }

    /**
     * @return Chassis if configured else null
     */
    public Chassis getChassis() {
        Optional<List<Component>> chassis = this.getConfiguration().entrySet().stream()
                .filter(e -> e.getKey().equals(CHASSIS))
                .map(Map.Entry::getValue)
                .findFirst();
        if (!chassis.isPresent() || chassis.get().isEmpty()) {
            log.infov("There is no chassis configured.");
            return null;
        }

        if (chassis.get().size() > 1)
            throw new IllegalStateException(String.format("Only one chassis can be configured - {0}", chassis.get()));
        return (Chassis) chassis.get().get(0);
    }

    /**
     * @param type The component type looked up for.
     * @return The components configured with the given type.
     */
    private List<Component> getComponentTypeConfigured(ComponentType type) {
        return ListUtils.emptyIfNull(this.configuration.get(type));
    }

    /**
     * Replace the configured chassis with the given one
     *
     * @param chassis The chassis to configure
     */
    private void replaceChassis(final @NonNull Chassis chassis) {
        this.replaceComponent(this.getChassis(), chassis);
    }


    /**
     * Replace the component to remove with the given one.
     *
     * @param toRemove The component to be replaced
     * @param with     The component to configure
     * @throws DifferentComponentTypeException
     */
    public void replaceComponent(final @NonNull Component toRemove, final @NonNull Component with) throws DifferentComponentTypeException {
        if (!toRemove.getComponentType().equals(with.getComponentType())) {
            throw new DifferentComponentTypeException(String.format(
                    "#replaceComponent - Cannot replace components of different types - toRemove {0} with {1}",
                    toRemove.getComponentType(), with.getComponentType()));
        }

        this.removeComponent(toRemove);
        this.addComponent(with, 1);
        log.debugv("#replaceComponent - Replaced {0} with {1}", toRemove, with);
    }

    /**
     * Remove the given component from the configuration.
     * Clean the configuration if component is a Chassis.
     *
     * @param toRemove The component to remove
     */
    public void removeComponent(final @NonNull Component toRemove) {
        if (!this.configuration.containsKey(toRemove.getComponentType())) {
            log.debugv("#removeComponent - Configuration key {0} not found", toRemove.getComponentType());
            return;
        }
        List<Component> components = Optional.ofNullable(this.configuration.get(toRemove.getComponentType()))
                .orElse(new ArrayList<>());
        List<Component> mutableComponents = new ArrayList<>();
        mutableComponents.addAll(components);
        final int indexToRemove = ListUtils.indexOf(mutableComponents, c -> c.equals(toRemove));

        if (indexToRemove == -1) { // ListUtils.indexOf returns -1 if components is null or empty
            throw new NotConfiguredComponentException(String.format("#removeComponent - Cannot remove not configured component - toRemove {0}", toRemove));
        }
        if (toRemove instanceof Chassis)
            this.clearConfiguration();
        else {
            mutableComponents.remove(indexToRemove);
            this.configuration.replace(toRemove.getComponentType(), mutableComponents);
            log.debugv("#removeComponent - Removed {0}", toRemove);
        }
    }

    /**
     * Remove all components from the configuration
     */
    public void clearConfiguration() {
        this.configuration.clear();
        log.debugv("#cleanConfiguration - Configuration cleaned {0}", this.getConfiguration());
    }

    /**
     * Publish the configuration
     */
    public void publishConfiguration() {
        this.setStatus(StarshipConfigurationStatus.PUBLISH);
        log.debugv("#publishConfiguration - Configuration status {0}", this.getStatus());
    }
}
