package org.starship.configurer.domain.models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class Component {
    private UUID id;
    private String name;
    private Manufacturer manufacturer;
    private double weight;
    private Size size;
    private ComponentType componentType;
}
