package org.example.exception.model;

import org.example.exception.handler.ExceptionHandler;
import lombok.Data;

@Data
public class ResponseResult {

    private int code;

    private String message;

    private Object data;

    private ResponseResult() {
    }

    private ResponseResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseResult success() {
        return new ResponseResult(ResultHandler.SUCCESS.getCode(), ResultHandler.SUCCESS.getMessage(), null);
    }

    public static ResponseResult success(String message, Object o) {
        return new ResponseResult(ResultHandler.SUCCESS.getCode(), message, o);
    }

    public static ResponseResult success(Object o) {
        return new ResponseResult(ResultHandler.SUCCESS.getCode(), ResultHandler.SUCCESS.getMessage(), o);
    }

    public static ResponseResult fail(ExceptionHandler handler) {
        return new ResponseResult(handler.getCode(), handler.getMessage(), null);
    }

    public static ResponseResult fail(ExceptionHandler handler, String message) {
        return new ResponseResult(handler.getCode(), message, null);
    }

    public static ResponseResult fail(String message) {
        return new ResponseResult(ResultHandler.SERVER_ERROR.getCode(), message, null);
    }

}
