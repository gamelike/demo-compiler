package org.example.enums;

public enum RelativeTimeType {
    TODAY("today"),
    YESTERDAY("yesterday"),
    WEEK("week"),
    LASTWEEK("lastWeek"),
    MONTH("month"),
    LASTMONTH("lastMonth"),
    YEAR("year"),
    QUARTER("quarter"),
    LASTONEMIN("lastOneMin"),
    LASTTENMIN("lastTenMin"),
    LASTTHIRTYMIN("lastThirtyMin"),
    LASTONEHOUR("lastOneHour"),
    LASTSEVENTDAY("lastSeventDay"),
    LASTTHIRTYDAY("lastThirtyDay"),
    PASSDAY("passday"),
    PASSTWODAY("passTwoday");

    private final String value;

    RelativeTimeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static RelativeTimeType parse(String value) {
        RelativeTimeType[] var1 = values();
        int var2 = var1.length;

        for (RelativeTimeType e : var1) {
            if (value.equals(e.getValue())) {
                return e;
            }
        }

        return null;
    }
}
