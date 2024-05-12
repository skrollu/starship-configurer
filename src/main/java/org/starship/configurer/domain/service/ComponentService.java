package org.starship.configurer.domain.service;

import org.starship.configurer.domain.model.Component;
import org.starship.configurer.domain.model.ComponentType;

import java.util.Set;
import java.util.UUID;

public interface ComponentService {
    Component saveComponent(Component component);
    Component searchComponent(UUID id, ComponentType componentType);
    Set<Component> searchComponents(ComponentType componentType);
}
