package org.example.constant;

/**
 * description:
 *
 * @author zhao_yifan
 * @since 2022/4/28
 */
public enum EvidenceType {

    DATA(0, "误报"),

    CLUE(1, "线索"),

    EVIDENCE(2, "证据");

    private final int code;
    private final String description;

    EvidenceType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
