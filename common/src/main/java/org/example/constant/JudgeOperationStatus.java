package org.example.constant;

/**
 * description:
 *
 * @author zhao_yifan
 * @date 2022/4/30
 */
public enum JudgeOperationStatus {

    /**
     * 自动研判
     */
    AUTO(1),

    /**
     * 手动研判
     */
    MANUAL(0);

    private final int status;

    JudgeOperationStatus(Integer status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

}
