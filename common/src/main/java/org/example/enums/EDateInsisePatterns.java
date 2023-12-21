package org.example.enums;

public enum EDateInsisePatterns {
    DATE_INSISE_PARTTEN_STAND_UNDERLINE_YEAR("_([1-2][0-9]{3})$"),
    DATE_INSISE_PARTTEN_STAND_UNDERLINE_MONTH("_([1-2][0-9]{3}-([0][0-9]|[1][0-2]))$"),
    DATE_INSISE_PARTTEN_STAND_UNDERLINE("_([1-2][0-9]{3}-([0][0-9]|[1][0-2])-([0][0-9]|[1][0-9]|[2][0-9]|[3][0-1]))$"),
    DATE_INSISE_PARTTEN_STAND_POINT("_([1-2][0-9]{3}.([0][0-9]|[1][0-2]).([0][0-9]|[1][0-9]|[2][0-9]|[3][0-1]))$"),
    DATE_INSISE_PARTTEN_STAND_SLASH("_([1-2][0-9]{3}/([0][0-9]|[1][0-2])/([0][0-9]|[1][0-9]|[2][0-9]|[3][0-1]))$"),
    DATE_INSISE_PARTTEN_STAND_UNDERLINE_MONTH_ROLL("_([1-2][0-9]{3}-([0][0-9]|[1][0-2])-([0-9]{6}))$"),
    DATE_INSISE_PARTTEN_STAND_UNDERLINE_HOUR("_([1-2][0-9]{3}-([0][0-9]|[1][0-2])-([0][0-9]|[1][0-9]|[2][0-9]|[3][0-1])_([0][0-9]|[1][0-9]|[2][0-4]))$"),
    DATE_INSISE_PARTTEN_COMPACT_UNDERLINE_MONTH("_([1-2][0-9]{3}([0][0-9]|[1][0-2]))$"),
    DATE_INSISE_PARTTEN_COMPACT_UNDERLINE("_([1-2][0-9]{3}([0][0-9]|[1][0-2])([0][0-9]|[1][0-9]|[2][0-9]|[3][0-1]))$"),
    DATE_INSISE_PARTTEN_COMPACT_UNDERLINE_HOUR("_([1-2][0-9]{3}([0][0-9]|[1][0-2])([0][0-9]|[1][0-9]|[2][0-9]|[3][0-1])_([0][0-9]|[1][0-9]|[2][0-4]))$"),
    DATE_INSISE_PARTTEN_COMPACT_YEAR("([1-2][0-9]{3})$"),
    DATE_INSISE_PARTTEN_COMPACT_MONTH("([1-2][0-9]{3}([0][0-9]|[1][0-2]))$"),
    DATE_INSISE_PARTTEN_COMPACT("([1-2][0-9]{3}([0][0-9]|[1][0-2])([0][0-9]|[1][0-9]|[2][0-9]|[3][0-1]))$"),
    DATE_INSISE_PARTTEN_COMPACT_HOUR("([1-2][0-9]{3}([0][0-9]|[1][0-2])([0][0-9]|[1][0-9]|[2][0-9]|[3][0-1])([0][0-9]|[1][0-9]|[2][0-4]))$");

    private final String value;

    EDateInsisePatterns(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static EDateInsisePatternType parse(String value) {
        EDateInsisePatternType[] var1 = EDateInsisePatternType.values();
        int var2 = var1.length;

        for (EDateInsisePatternType e : var1) {
            if (value.equals(e.getValue())) {
                return e;
            }
        }

        return null;
    }
}
