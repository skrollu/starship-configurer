package org.starship.configurer.domain.service;

import org.starship.configurer.domain.model.StarshipConfiguration;

import java.util.UUID;

public interface StarshipConfigurationService {

    StarshipConfiguration getConfigurationById(UUID id);
    void updateConfiguration(UUID id);
    void cleanConfiguration(UUID id);

}
