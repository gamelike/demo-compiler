package org.example.constant;

import org.example.exception.ArgsException;

/**
 * @author violet
 * @since 2023/5/11
 */
public enum Severity {
    message("信息", 4),
    low("低危", 3),
    middle("中危", 2),
    high("高危", 1),
    important("严重", 0);
    private final String cnName;
    private final int sorted;

    Severity(String cnName, int sorted) {
        this.cnName = cnName;
        this.sorted = sorted;
    }

    public String getCnName() {
        return cnName;
    }

    public int getSorted() {
        return sorted;
    }

    public static int cnName2Sorted(String cnName) {
        for (Severity value : Severity.values()) {
            if (value.cnName.equals(cnName)) {
                return value.sorted;
            }
        }
        throw new ArgsException("不存在该" + cnName + "风险等级");
    }
}
