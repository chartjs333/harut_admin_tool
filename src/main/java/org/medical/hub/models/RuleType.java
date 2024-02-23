package org.medical.hub.models;

import java.util.Arrays;

public enum RuleType {

    ROW(1, "Row"), COLUMN(2, "Column");

    private final String name;
    private final int value;

    RuleType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public int getValue(){
        return this.value;
    }

    /**
     * @return the Enum representation for the given string.
     * @throws IllegalArgumentException if unknown string.
     */
    public static RuleType fromString(int value) throws IllegalArgumentException {
        return Arrays.stream(RuleType.values())
                .filter(v -> v.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown value: " + value));
    }
}
