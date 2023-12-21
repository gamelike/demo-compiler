package org.example.enums;

public enum EFieldDataType {
    NUMBER("number"),
    STRING("string"),
    BOOLEAN("boolean"),
    IP("ip"),
    TIMESTAMP("timestamp"),
    GEO_POINT("geo_point"),
    UNDEFIND("undefind");

    private final String value;

    EFieldDataType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static EFieldDataType parse(String value) {
        EFieldDataType[] var1 = values();
        int var2 = var1.length;

        for (EFieldDataType e : var1) {
            if (value.equals(e.getValue())) {
                return e;
            }
        }

        return null;
    }
}
