package org.example.constant;

/**
 * 异常线索类型
 *
 * @author zhap_yifan
 */
public enum ClueType {

    /**
     * 异常线索
     */
    EXCEPTION(0),

    /**
     * 异常线索现象
     */
    EXCEPTION_PHENOMENON(1);

    private final Integer type;

    ClueType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

}
