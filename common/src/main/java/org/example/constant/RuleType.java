package org.example.constant;

public enum RuleType {

    ipConfidence("IP"),
    devConfidence("设备"),
    ruleConfidence("规则"),
    whitelist("白名单"),
    tib("威胁情报"),
    evidence("证据标注"),
    complete("完成研判");

    private final String description;

    RuleType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
