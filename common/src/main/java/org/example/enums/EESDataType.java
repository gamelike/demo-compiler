package org.example.enums;

public enum EESDataType {

    INTEGER("integer"),
    FLOAT("float"),
    LONG("long"),
    BIGINT("bigInt"),
    TEXT("text"),
    KEYWORD("keyword"),
    STRING("string"),
    BOOLEAN("boolean"),
    IP("ip"),
    UNDEFIND("undefind"),
    DATE("date"),
    GEO_POINT("geo_point"),
    DOUBLE("double"),
    BYTES("bytes");

    private final String value;

    EESDataType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static EESDataType parse(String value) {
        EESDataType[] var1 = values();
        int var2 = var1.length;

        for (EESDataType e : var1) {
            if (value.equals(e.getValue())) {
                return e;
            }
        }

        return null;
    }
}
