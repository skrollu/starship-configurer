package org.starship.configurer.connector.dynamodb.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import static lombok.AccessLevel.PRIVATE;
import static org.starship.configurer.connector.dynamodb.entity.ComponentDescriptors.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@SuperBuilder
@DynamoDbBean
public class DynamoDbComponent extends DynamoDbItem {

    @Getter(onMethod = @__({@DynamoDbAttribute(COL_NAME)}))
    @NonNull
    private String name;
    @Getter(onMethod = @__({@DynamoDbAttribute(COL_MANUFACTURER)}))
    @NonNull
    private String manufacturer;
    @Getter(onMethod = @__({@DynamoDbAttribute(COL_WEIGHT)}))
    private double weight;
    @Getter(onMethod = @__({@DynamoDbAttribute(COL_CLASS_SIZE)}))
    @NonNull
    private String classSize;
}
