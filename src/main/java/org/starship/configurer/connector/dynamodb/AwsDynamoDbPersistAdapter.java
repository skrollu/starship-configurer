package org.starship.configurer.connector.dynamodb;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.starship.configurer.connector.dynamodb.entity.DynamoDbComponentMapper;
import org.starship.configurer.connector.dynamodb.entity.DynamoDbComponent;
import org.starship.configurer.domain.model.ComponentItem;
import org.starship.configurer.domain.model.ComponentType;
import org.starship.configurer.domain.service.ComponentPersistAdapter;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.starship.configurer.connector.dynamodb.entity.ComponentDescriptors.TABLE_NAME;
import static org.starship.configurer.connector.dynamodb.entity.DynamoDbDescriptors.SK_INFO;

@JBossLog
@AllArgsConstructor
@ApplicationScoped
public class AwsDynamoDbPersistAdapter implements ComponentPersistAdapter {

    @Inject
    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    private DynamoDbTable<DynamoDbComponent> componentTable;

    @Inject
    private final DynamoDbComponentMapper mapper;

    @PostConstruct
    private void init() {
        componentTable = dynamoDbEnhancedClient.table(TABLE_NAME, TableSchema.fromBean(DynamoDbComponent.class));
        log.debug("AwsDynamoDbPersistAdapter initiated");
    }

    @Override
    public void putComponent(ComponentItem componentItem) {
        DynamoDbComponent ddbComponent = mapper.toItem(componentItem);
        componentTable.putItem(ddbComponent);
    }

    @Override
    public ComponentItem getComponent(UUID id) {
        Key primaryKey = Key.builder()
                .partitionValue(mapper.doPartitionCOMP(id))
                .sortValue(SK_INFO)
                .build();
        DynamoDbComponent ddbComp = componentTable.getItem(primaryKey);
        if (Objects.isNull(ddbComp)) {
            log.warnv("ComponentItem {0} unknown", id);
            return null;
        }
//        return mapper.toModel(ddbComp);
        return  null;
    }

    @Override
    public List<ComponentItem> getComponents(ComponentType type) {
        return null;
    }

    @Override
    public void updateComponent(UUID id, ComponentItem componentItem) {

    }

    @Override
    public void deleteComponent(UUID id) {

    }
}
