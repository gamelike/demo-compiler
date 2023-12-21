package org.example.enums;

public enum ETimeFieldType {
    TIMEFIELD_FORMAT_TIMESTAMP("1"),
    TIMEFIELD_FORMAT_DEFAULT("2"),
    TIMEFIELD_FORMAT_STAND("3");

    private final String value;

    ETimeFieldType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static ETimeFieldType parse(String value) {
        ETimeFieldType[] var1 = values();
        int var2 = var1.length;

        for (ETimeFieldType e : var1) {
            if (value.equals(e.getValue())) {
                return e;
            }
        }

        return null;
    }
}
