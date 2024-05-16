package org.starship.configurer.connector.dynamodb.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@SuperBuilder
@DynamoDbBean
public class DynamoDbItem {

    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute(DynamoDbDescriptors.COL_PK)}))
    @NonNull
    private String partitionKey;

    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute(DynamoDbDescriptors.COL_SK)}))
    private String sortKey;

    @Getter(onMethod = @__({@DynamoDbAttribute(DynamoDbDescriptors.COL_ITEM_TYPE)}))
    @NonNull
    private String itemType;
}
