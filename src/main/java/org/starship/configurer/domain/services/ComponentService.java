package org.starship.configurer.domain.services;

import org.starship.configurer.domain.models.Component;
import org.starship.configurer.domain.models.ComponentType;

import java.util.Set;
import java.util.UUID;

public interface ComponentService {
    Component saveComponent(Component component);
    Component searchComponent(UUID id, ComponentType componentType);
    Set<Component> searchComponents(ComponentType componentType);
}
