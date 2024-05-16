package org.starship.configurer.domain.service;

import org.starship.configurer.domain.model.Component;
import org.starship.configurer.domain.model.ComponentType;

import java.util.Set;
import java.util.UUID;

public interface ComponentService {
    Component createComponent(Component component);
    Component searchComponent(UUID id);
    Set<Component> searchComponents(ComponentType componentType);

    Component updateComponent(UUID id, Component component);

    void deleteComponent(UUID id);
}
