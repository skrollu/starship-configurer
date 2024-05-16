package org.starship.configurer.domain.service;

import lombok.AllArgsConstructor;
import lombok.With;
import lombok.extern.jbosslog.JBossLog;
import org.starship.configurer.domain.model.Component;
import org.starship.configurer.domain.model.ComponentType;

import java.util.Set;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@JBossLog
@AllArgsConstructor(access = PRIVATE)
public class ComponentServiceImpl implements ComponentService {

    @With
    private final ComponentPersistAdapter persist;

    @Override
    public Component createComponent(Component component) {
        component.setId(UUID.randomUUID());
        persist.putComponent(component);
        return component;
    }

    @Override
    public Component searchComponent(UUID id) {
        return this.persist.getComponent(id);
    }

    @Override
    public Set<Component> searchComponents(ComponentType componentType) {
        // TODO
        return null;
    }

    @Override
    public Component updateComponent(UUID id, Component component) {
        // TODO
        persist.updateComponent(id, component);
        return null;
    }

    @Override
    public void deleteComponent(UUID id) {
        persist.deleteComponent(id);
    }
}
