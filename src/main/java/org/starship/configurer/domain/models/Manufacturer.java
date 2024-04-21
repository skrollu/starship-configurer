package org.starship.configurer.domain.models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class Manufacturer {
    private UUID id;
    private String name;
}
