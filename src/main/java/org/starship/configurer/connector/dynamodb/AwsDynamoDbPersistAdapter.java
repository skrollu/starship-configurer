package org.starship.configurer.connector.dynamodb;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;
import org.starship.configurer.connector.dynamodb.entity.ComponentMapper;
import org.starship.configurer.connector.dynamodb.entity.DynamoDbComponent;
import org.starship.configurer.domain.model.Component;
import org.starship.configurer.domain.service.ComponentPersistAdapter;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.UUID;

import static org.starship.configurer.connector.dynamodb.entity.ComponentDescriptors.TABLE_NAME;

@JBossLog
@AllArgsConstructor
@ApplicationScoped
public class AwsDynamoDbPersistAdapter implements ComponentPersistAdapter {

    @Inject
    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    private DynamoDbTable<DynamoDbComponent> componentTable;

    @Inject
    private final ComponentMapper mapper;

    @PostConstruct
    private void init() {
        componentTable = dynamoDbEnhancedClient.table(TABLE_NAME, TableSchema.fromBean(DynamoDbComponent.class));
        log.debug("AwsDynamoDbPersistAdapter initiated");
    }

    @Override
    public void putComponent(Component component) {
        DynamoDbComponent ddbComponent = mapper.toItem(component);
        componentTable.putItem(ddbComponent);
    }

    @Override
    public Component getComponent(UUID id) {
        return null;
    }

    @Override
    public void updateComponent(UUID id, Component component) {

    }

    @Override
    public void deleteComponent(UUID id) {

    }
}
