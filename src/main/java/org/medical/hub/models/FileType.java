package org.medical.hub.models;

import java.util.Arrays;

public enum FileType {
    CLINICAL_DATA("Clinical Data"),
    SAMPLE_MANIFEST("Sample Manifest"),
    FACTOR_PRIOR_TO_PD("Factors Prior to PD");

    private final String value;

    FileType(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    /**
     * @return the Enum representation for the given string.
     * @throws IllegalArgumentException if unknown string.
     */
    public static FileType fromString(String s) throws IllegalArgumentException {
        return Arrays.stream(FileType.values())
                .filter(v -> v.value.equals(s))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown value: " + s));
    }
}
