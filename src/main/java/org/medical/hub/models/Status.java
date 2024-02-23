package org.medical.hub.models;

import java.util.Arrays;

public enum Status {

    APPROVED("Approved"), IN_PROGRESS("In Progress"), SUBMITTED("Submitted"), DELETED("Deleted") ;

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    /**
     * @return the Enum representation for the given string.
     * @throws IllegalArgumentException if unknown string.
     */
    public static Status fromString(String s) throws IllegalArgumentException {
        return Arrays.stream(Status.values())
                .filter(v -> v.value.equals(s))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown value: " + s));
    }
}
