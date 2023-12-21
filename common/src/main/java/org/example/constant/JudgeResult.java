package org.example.constant;

import org.example.exception.ArgsException;

/**
 * description:
 *
 * @author zhao_yifan
 * @date 2022/5/1
 */
public enum JudgeResult {

    /**
     * 误报.
     */
    FALSE(0, "误报"),

    /**
     * 确认攻击.
     */
    TRUE(1, "确认攻击"),

    /**
     * 未知.
     */
    UNKNOWN(2, "未知"),

    /**
     * 攻击并造成影响.
     */
    INFLUENCE(3, "攻击并造成影响");

    private final Integer type;
    private final String description;

    JudgeResult(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    public Integer getType() {
        return this.type;
    }

    public String getDescription() {
        return description;
    }

    public static String code2Description(int code) {
        for (JudgeResult value : JudgeResult.values()) {
            if (value.getType().equals(code)) {
                return value.getDescription();
            }
        }
        throw new ArgsException("不存在code为 " + code + "的研判结果");
    }

    public static int description2Code(String description) {
        for (JudgeResult value : JudgeResult.values()) {
            if (value.getDescription().equals(description)) {
                return value.getType();
            }
        }
        throw new ArgsException("不存在description为 " + description + "的研判结果");
    }
}
