package org.starship.configurer.domain.service;

import org.starship.configurer.domain.model.ComponentItem;
import org.starship.configurer.domain.model.ComponentType;

import java.util.List;
import java.util.UUID;

public interface ComponentPersistAdapter {

    void putComponent(ComponentItem componentItem);
    ComponentItem getComponent(UUID id);
    List<ComponentItem> getComponents(ComponentType type);
    void updateComponent(UUID id, ComponentItem componentItem);
    void deleteComponent(UUID id);

}
