package org.starship.configurer.domain.models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jbosslog.JBossLog;
import org.starship.configurer.domain.models.components.Chassis;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
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

    public int howManyCompatibleComponentAllowed(Component component) {
        Chassis chassis = this.getChassis();
        return chassis.howManyCompatibleComponentAllowed(component);
    }

    public Chassis getChassis() {
        Optional<Component> opt = this.getComponents().stream()
                .filter(c -> c.isChassis())
                .findFirst();
        if (!opt.isPresent())
            log.infov("There is no chassis configured.");
        return (Chassis) opt.get();
    }

    private Set<Component> getSameComponents(Component component) {
        return this.components.stream()
                .filter(c -> c.getComponentType().equals(component.getComponentType()))
                .collect(Collectors.toSet());
    }

    private void replaceChassis(Chassis chassis) {
        this.replaceComponent(this.getChassis(), chassis);
    }

    private void replaceComponent(Component toRemove, Component with) {
        this.getComponents().remove(toRemove);
        this.getComponents().add(with);
        log.infov("Replaced {0}  with {1}", toRemove, with);
    }
}
