package org.example.enums;

public enum RelativeTimeUnit {
    SECONDS("seconds"),
    MINUTE("minute"),
    HOUR("hour"),
    DAY("day"),
    WEEK("week"),
    MONTH("month"),
    YEAR("year");

    private final String value;

    RelativeTimeUnit(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static RelativeTimeUnit parse(String value) {
        RelativeTimeUnit[] var1 = values();
        int var2 = var1.length;

        for (RelativeTimeUnit e : var1) {
            if (value.equals(e.getValue())) {
                return e;
            }
        }

        return null;
    }
}