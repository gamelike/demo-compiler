package org.example.exception.model;

import org.example.exception.handler.ExceptionHandler;

/**
 * @author violet
 */
public enum ResultHandler implements ExceptionHandler {

    SUCCESS(0, "成功响应"),
    ARGS_FAIL(4004, "参数校验失败"),
    UNKNOWN(4000, "服务器未知错误"),

    REMOTE_CALLED_ERROR(5000, "RPC调用失败"),
    SERVER_ERROR(1, "服务器错误");

    private final int resultCode;

    private final String resultMsg;


    ResultHandler(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public int getCode() {
        return this.resultCode;
    }

    @Override
    public String getMessage() {
        return this.resultMsg;
    }

}
