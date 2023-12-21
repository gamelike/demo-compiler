package org.example.constant;

/**
 * description:
 *
 * @author zhao_yifan
 * @date 2022/5/1
 */
public enum JudgeStatus {

    COMPLETE("研判完成"),

    PROCESSING("研判中");

    private final String status;

    JudgeStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

}
