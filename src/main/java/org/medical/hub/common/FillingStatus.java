package org.medical.hub.common;

public enum FillingStatus {

    EXCEL_UPLOADED("60"),
    NOT_STARTED("0");

    private final String value;

    FillingStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
