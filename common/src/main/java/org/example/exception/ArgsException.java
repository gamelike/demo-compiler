package org.example.exception;

import org.example.exception.model.ResultHandler;

/**
 * @author violet
 */
public class ArgsException extends RuntimeException {

    private final ResultHandler resultHandler;

    public ResultHandler getResultHandler() {
        return resultHandler;
    }

    public ArgsException(String message) {
        super(message);
        resultHandler = ResultHandler.ARGS_FAIL;
    }

    public ArgsException(String message, Throwable cause) {
        super(message, cause);
        resultHandler = ResultHandler.ARGS_FAIL;
    }
}
