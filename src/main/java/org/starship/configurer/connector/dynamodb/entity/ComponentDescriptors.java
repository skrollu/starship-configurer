package org.starship.configurer.connector.dynamodb.entity;

public class ComponentDescriptors {

    public static final String TABLE_NAME = "Component";

    // ==== component dynamodb model
    public static final String PFX_COMPONENT = "COMP";
    public static final String COL_NAME = "Name";
    public static final String COL_MANUFACTURER = "Manufacturer";
    public static final String COL_WEIGHT = "Weight";
    public static final String COL_CLASS_SIZE = "ClassSize";

    // ==== chassis dynamodb model
    public static final String COL_POSSIBILITIES = "Possibilities";

    // ==== engine dynamodb model
    public static final String COL_THRUST_POWER = "ThrustPower";

}
