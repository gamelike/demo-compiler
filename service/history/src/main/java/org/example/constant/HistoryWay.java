package org.example.constant;

/**
 * @author violet
 * @since 2023/4/26
 */
public enum HistoryWay {
    automation("自动生成"),
    operation("操作产生");
    private final String description;

    HistoryWay(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
