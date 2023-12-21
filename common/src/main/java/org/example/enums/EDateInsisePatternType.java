package org.example.enums;


public enum EDateInsisePatternType {
    DATE_INSISE_PARTTEN_STAND_UNDERLINE_YEAR("_yyyy"),
    DATE_INSISE_PARTTEN_STAND_UNDERLINE_MONTH("_yyyy-MM"),
    DATE_INSISE_PARTTEN_STAND_UNDERLINE("_yyyy-MM-dd"),
    DATE_INSISE_PARTTEN_STAND_POINT("_yyyy.MM.dd"),
    DATE_INSISE_PARTTEN_STAND_SLASH("_yyyy/MM/dd"),
    DATE_INSISE_PARTTEN_STAND_UNDERLINE_HOUR("_yyyy-MM-dd_HH"),
    DATE_INSISE_PARTTEN_COMPACT_UNDERLINE_MONTH("_yyyyMM"),
    DATE_INSISE_PARTTEN_STAND_UNDERLINE_MONTH_ROLL("_yyyy-MM-dddddd"),
    DATE_INSISE_PARTTEN_COMPACT_UNDERLINE("_yyyyMMdd"),
    DATE_INSISE_PARTTEN_COMPACT_UNDERLINE_HOUR("_yyyyMMdd_HH"),
    DATE_INSISE_PARTTEN_COMPACT_YEAR("yyyy"),
    DATE_INSISE_PARTTEN_COMPACT_MONTH("yyyyMM"),
    DATE_INSISE_PARTTEN_COMPACT("yyyyMMdd"),
    DATE_INSISE_PARTTEN_COMPACT_HOUR("yyyyMMddHH");

    private final String value;

    EDateInsisePatternType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static EDateInsisePatternType parse(String value) {
        EDateInsisePatternType[] var1 = values();
        int var2 = var1.length;

        for (EDateInsisePatternType e : var1) {
            if (value.equals(e.getValue())) {
                return e;
            }
        }

        return null;
    }
}