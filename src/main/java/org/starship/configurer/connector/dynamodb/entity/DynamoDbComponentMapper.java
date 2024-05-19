package org.starship.configurer.connector.dynamodb.entity;

import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;
import org.starship.configurer.domain.model.ComponentItem;
import org.starship.configurer.domain.model.components.Chassis;

import java.util.UUID;

import static org.mapstruct.MappingConstants.ComponentModel.CDI;
import static org.starship.configurer.connector.dynamodb.entity.ComponentDescriptors.PFX_COMPONENT;
import static org.starship.configurer.connector.dynamodb.entity.DynamoDbDescriptors.DELIMITER;
import static org.starship.configurer.connector.dynamodb.entity.DynamoDbDescriptors.SK_INFO;

@Mapper(componentModel = CDI, subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface DynamoDbComponentMapper {

    @Mapping(target = "partitionKey", expression = "java(doPartitionCOMP(model.getId()))")
    @Mapping(target = "sortKey", constant = SK_INFO)
    @Mapping(target = "classSize", source = "size.value")
    @SubclassMapping(target = DynamoDbChassis.class, source = Chassis.class)
    DynamoDbComponent toItem(ComponentItem model);
    ComponentItem toModel(DynamoDbComponent item);

    default String doPartitionCOMP(@NonNull final UUID id) {
        return String.join(DELIMITER, PFX_COMPONENT, id.toString());
    }

}
