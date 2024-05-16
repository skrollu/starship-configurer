package org.starship.configurer.connector.dynamodb.entity;

import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.starship.configurer.domain.model.Component;
import org.starship.configurer.domain.model.components.Chassis;

import java.util.UUID;

import static org.mapstruct.MappingConstants.ComponentModel.CDI;
import static org.starship.configurer.connector.dynamodb.entity.ComponentDescriptors.PFX_COMPONENT;
import static org.starship.configurer.connector.dynamodb.entity.DynamoDbDescriptors.DELIMITER;
import static org.starship.configurer.connector.dynamodb.entity.DynamoDbDescriptors.SK_INFO;

@Mapper(componentModel = CDI)
public interface ComponentMapper {

    @Mapping(target = "partitionKey", expression = "java(doPartitionCOMP(model.getId()))")
    @Mapping(target = "sortKey", constant = SK_INFO)
    @Mapping(target = "classSize", source = "size.value")
    DynamoDbComponent toItem(Chassis model);
    Chassis toModel(DynamoDbComponent item);

    default String doPartitionCOMP(@NonNull final UUID id) {
        return String.join(DELIMITER, PFX_COMPONENT, id.toString());
    }

}
