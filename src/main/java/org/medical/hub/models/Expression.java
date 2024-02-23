package org.medical.hub.models;

import java.util.Arrays;

public enum Expression {

    EQUAL(1, "Equal(=)"),
    LESS_THAN(2, "Less Than(<)"),
    LESS_THAN_EQUAL(3, "Less than or Equal to(<=)"),
    GREATER_THAN(4, "Greater Than(>)"),
    GREATER_THAN_EQUAL(5, "Greater Than or Equal(>=)"),
    NOT_EQUAL_TO(6, "Not equal to(!=)");

    private final String name;
    private final int value;

    Expression(int value, String name) {
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
    public static Expression fromString(int value) throws IllegalArgumentException {
        return Arrays.stream(Expression.values())
                .filter(v -> v.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown value: " + value));
    }
}
