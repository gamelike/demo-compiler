package org.example.constant;

/**
 * @author violet
 * @since 2023/4/26
 */
public enum RecordType {
    record(HistoryWay.operation),
    deleted(HistoryWay.operation),
    tib(HistoryWay.automation),
    confidence(HistoryWay.automation),
    white(HistoryWay.automation),
    clue(HistoryWay.operation);
    private final HistoryWay historyWay;

    RecordType(HistoryWay historyWay) {
        this.historyWay = historyWay;
    }

    public HistoryWay getHistoryWay() {
        return historyWay;
    }
}
