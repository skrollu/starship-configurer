package org.starship.configurer.domain.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.extern.jbosslog.JBossLog;
import org.starship.configurer.domain.model.ComponentItem;
import org.starship.configurer.domain.model.ComponentType;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@JBossLog
@AllArgsConstructor(access = PRIVATE)
@Getter
@NoArgsConstructor
public class ComponentServiceImpl implements ComponentService {

    @With
    private ComponentPersistAdapter persist;

    @Override
    public ComponentItem createComponent(ComponentItem componentItem) {
        componentItem.setId(UUID.randomUUID());
        persist.putComponent(componentItem);
        return componentItem;
    }

    @Override
    public ComponentItem searchComponent(UUID id) {
        return this.persist.getComponent(id);
    }

    @Override
    public Set<ComponentItem> searchComponents(ComponentType componentType) {
        return this.persist.getComponents(componentType).stream().collect(Collectors.toSet());
    }

    @Override
    public ComponentItem updateComponent(UUID id, ComponentItem componentItem) {
        throw new IllegalStateException(String.format("Update componentItem is not implemented yet. ComponentItem versioning must be handle before for data integrity."));
    }

    @Override
    public void deleteComponent(UUID id) {
        persist.deleteComponent(id);
    }
}
