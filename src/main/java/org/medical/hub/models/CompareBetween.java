package org.medical.hub.models;

import java.util.Arrays;

public enum CompareBetween {

    BETWEEN_COLUMNS(1, "Values between Columns"),
    COL_WITH_PARAMETER(2, "Values from column to parameter"),
    VALIDATE_COLUMN(3, "Validate the column");

    private final String name;
    private final int value;

    CompareBetween(int value, String name) {
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
    public static CompareBetween fromString(int value) throws IllegalArgumentException {
        return Arrays.stream(CompareBetween.values())
                .filter(v -> v.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown value: " + value));
    }
}
