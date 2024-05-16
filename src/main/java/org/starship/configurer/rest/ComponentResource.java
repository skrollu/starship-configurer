package org.starship.configurer.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.starship.configurer.domain.model.Component;
import org.starship.configurer.domain.model.ComponentType;
import org.starship.configurer.domain.service.ComponentService;

import java.util.Set;
import java.util.UUID;

@Path("/components")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ComponentResource {

    @Inject
    private ComponentService service;

    @GET
    public Component getComponent(UUID id) {
        return this.service.searchComponent(id);
    }

    @GET
    public Set<Component> getByComponentType(ComponentType componentType) {
        return this.service.searchComponents(componentType);
    }

    @POST
    public Component postComponent(Component component) {
        return this.service.createComponent(component);
    }
}
