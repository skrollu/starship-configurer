package org.starship.configurer.domain.service;

import org.starship.configurer.domain.model.ComponentItem;
import org.starship.configurer.domain.model.ComponentType;

import java.util.Set;
import java.util.UUID;

public interface ComponentService {
    ComponentItem createComponent(ComponentItem componentItem);
    ComponentItem searchComponent(UUID id);
    Set<ComponentItem> searchComponents(ComponentType componentType);

    ComponentItem updateComponent(UUID id, ComponentItem componentItem);

    void deleteComponent(UUID id);
}
