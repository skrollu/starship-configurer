package org.starship.configurer.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jbosslog.JBossLog;
import org.apache.commons.collections4.ListUtils;
import org.starship.configurer.domain.model.components.Chassis;
import org.starship.configurer.domain.model.exceptions.DifferentComponentTypeException;
import org.starship.configurer.domain.model.exceptions.NotConfiguredComponentException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.starship.configurer.domain.model.ComponentType.CHASSIS;

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
    private String manufacturer;
    @NonNull
    @Builder.Default
    // TODO work on ComponentItem hashcode
    private Map<ComponentType, List<ComponentItem>> configuration = new HashMap<>();
    @NonNull
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt; // TODO check value
    @NonNull
    @Builder.Default
    private StarshipConfigurationStatus status = StarshipConfigurationStatus.DRAFT; // TODO manage draft status

    /**
     * Add the given componentItem to the configuration
     *
     * @param componentItem          The componentItem to add to the configuration
     * @param numberOfComponents The number of the given componentItem to add to the configuration, ignored if the componentItem is a chassis
     */
    public void addComponent(@NonNull final ComponentItem componentItem, final int numberOfComponents) {
        boolean isChassisConfigured = isChassisConfigured();
        boolean isComponentAChassis = componentItem instanceof Chassis;

        // handle chassis
        if (!isChassisConfigured && !isComponentAChassis) {
            throw new IllegalStateException(String.format("Chassis must be configured first"));
        } else if (isChassisConfigured && isComponentAChassis) { // TODO manage numberOfComponents here
            this.replaceChassis((Chassis) componentItem);
            return;
        } else if (!isChassisConfigured && isComponentAChassis) {
            List<ComponentItem> chassis = new ArrayList<>();
            chassis.add(componentItem);
            configuration.put(CHASSIS, chassis);
            return;
        }

        // handle other components
        int allowed = this.howManyCompatibleComponentAllowed(componentItem);
        List<ComponentItem> configuredComponentItem = this.getComponentTypeConfigured(componentItem.getComponentType());
        if ((configuredComponentItem.size() + numberOfComponents) > allowed) {
            throw new IllegalStateException(String.format("Cannot add {0} components, too many {1} configured", numberOfComponents, componentItem.getComponentType()));
        }
        List<ComponentItem> result = new ArrayList<>();
        result.addAll(configuredComponentItem);
        for (int i = 0; i < numberOfComponents; i++) {
            result.add(componentItem);
        }
        configuration.put(componentItem.getComponentType(), result);
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
     * Retrieve chassis and check how many given componentItem are compatible
     *
     * @return <br/> 0 if chassis isn't configured
     * <br/> how many compatible components are allowed
     */
    public int howManyCompatibleComponentAllowed(final @NonNull ComponentItem componentItem) {
        Chassis chassis = this.getChassis();
        if (Objects.isNull(chassis))
            return 0;
        return chassis.howManyCompatibleComponentAllowed(componentItem, 1);
    }

    /**
     * @return Chassis if configured else null
     */
    public Chassis getChassis() {
        Optional<List<ComponentItem>> chassis = this.getConfiguration().entrySet().stream()
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
    private List<ComponentItem> getComponentTypeConfigured(ComponentType type) {
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
    public void replaceComponent(final @NonNull ComponentItem toRemove, final @NonNull ComponentItem with) throws DifferentComponentTypeException {
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
    public void removeComponent(final @NonNull ComponentItem toRemove) {
        if (!this.configuration.containsKey(toRemove.getComponentType())) {
            log.debugv("#removeComponent - Configuration key {0} not found", toRemove.getComponentType());
            return;
        }
        List<ComponentItem> componentItems = Optional.ofNullable(this.configuration.get(toRemove.getComponentType()))
                .orElse(new ArrayList<>());
        List<ComponentItem> mutableComponentItems = new ArrayList<>();
        mutableComponentItems.addAll(componentItems);
        final int indexToRemove = ListUtils.indexOf(mutableComponentItems, c -> c.equals(toRemove));

        if (indexToRemove == -1) { // ListUtils.indexOf returns -1 if componentItems is null or empty
            throw new NotConfiguredComponentException(String.format("#removeComponent - Cannot remove not configured component - toRemove {0}", toRemove));
        }
        if (toRemove instanceof Chassis)
            this.clearConfiguration();
        else {
            mutableComponentItems.remove(indexToRemove);
            this.configuration.replace(toRemove.getComponentType(), mutableComponentItems);
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
