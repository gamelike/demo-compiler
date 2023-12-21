package org.example.constant;

/**
 * @author violet
 * @since 2023/5/4
 */
public enum HistoryRecordMapping {
    id("_id"),
    belongId("belong_id.keyword"),
    relationId("relation_id.keyword"),
    type("type"),
    eventTime("event_time"),
    createTime("create_time"),
    recordType("record_type.keyword"),
    score("score"),
    toolBox("tool_box.keyword"),
    way("way"),
    description("description"),
    process("process"),
    keyFeature("keyFeature.keyword"),
    marked("marked");

    private final String mapping;


    HistoryRecordMapping(String mapping) {
        this.mapping = mapping;
    }

    public String getMapping() {
        return mapping;
    }
}
