package org.starship.configurer.rest;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.jbosslog.JBossLog;
import org.starship.configurer.domain.model.ComponentItem;
import org.starship.configurer.domain.model.ComponentType;
import org.starship.configurer.domain.service.ComponentPersistAdapter;
import org.starship.configurer.domain.service.ComponentService;
import org.starship.configurer.domain.service.ComponentServiceImpl;

import java.util.Set;
import java.util.UUID;

@Path("/components")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@JBossLog
public class ComponentResource {

    @Inject
    private ComponentService service;

    @Inject
    private Instance<ComponentPersistAdapter> persist;

    @PostConstruct
    void init() {
        service = new ComponentServiceImpl()
                .withPersist(this.persist.isResolvable() ? this.persist.get() : null);
        log.info("ComponentResource initiated");
    }

    @GET
    public ComponentItem getComponent(UUID id) {
        return this.service.searchComponent(id);
    }

    @GET
    public Set<ComponentItem> getByComponentType(ComponentType componentType) {
        return this.service.searchComponents(componentType);
    }

    @POST
    public ComponentItem postComponent(ComponentItem componentItem) {
        return this.service.createComponent(componentItem);
    }

    @PUT
    public ComponentItem putComponent(UUID id, ComponentItem componentItem) {
        return this.service.updateComponent(id, componentItem);
    }
}
