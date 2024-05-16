package org.starship.configurer.domain.service;

import org.starship.configurer.domain.model.Component;

import java.util.UUID;

public interface ComponentPersistAdapter {

    void putComponent(Component component);
    Component getComponent(UUID id);
    void updateComponent(UUID id, Component component);
    void deleteComponent(UUID id);

}
