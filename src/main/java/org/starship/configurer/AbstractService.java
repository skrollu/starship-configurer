package org.starship.configurer;

import java.util.HashMap;
import java.util.Map;

import software.amazon.awssdk.services.dynamodb.model.*;

public abstract class AbstractService {

    //    public static final String FRUIT_ID_COL = "id";
    public static final String FRUIT_NAME_COL = "fruitName";
    public static final String FRUIT_DESC_COL = "fruitDescription";

    public String getTableName() {
        return "Fruits";
    }

    protected ScanRequest scanRequest() {
        return ScanRequest.builder().tableName(getTableName())
                .attributesToGet(FRUIT_NAME_COL, FRUIT_DESC_COL).build();
    }

    protected ListTablesRequest listTables() {
        return ListTablesRequest.builder().build();
    }

    protected PutItemRequest putRequest(Fruit fruit) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put(FRUIT_NAME_COL, AttributeValue.builder().s(fruit.getName()).build());
        item.put(FRUIT_DESC_COL, AttributeValue.builder().s(fruit.getDescription()).build());

        return PutItemRequest.builder()
                .tableName(getTableName())
                .item(item)
                .build();
    }

    protected GetItemRequest getRequest(String name) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put(FRUIT_NAME_COL, AttributeValue.builder().s(name).build());

        return GetItemRequest.builder()
                .tableName(getTableName())
                .key(key)
                .attributesToGet(FRUIT_NAME_COL, FRUIT_DESC_COL)
                .build();
    }
}
