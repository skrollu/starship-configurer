package org.starship.configurer.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.starship.configurer.domain.models.Component;
import org.starship.configurer.domain.models.ComponentType;
import org.starship.configurer.domain.services.ComponentService;

import java.util.Set;
import java.util.UUID;

@Path("/components")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ComponentResource {

    @Inject
    private ComponentService service;

    @GET
    public Component getComponent(UUID id, ComponentType componentType) {
        return this.service.searchComponent(id, componentType);
    }

    @GET
    public Set<Component> getByComponentType(ComponentType componentType) {
        return this.service.searchComponents(componentType);
    }

    @GET
    public Component saveComponent(Component component) {
        return this.service.saveComponent(component);
    }
}
