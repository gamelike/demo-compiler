package org.example.exception;

import org.example.exception.handler.ExceptionHandler;
import org.example.exception.model.ResultHandler;

/**
 * @author violet
 */
public class JudgeException extends RuntimeException {
    private final ExceptionHandler exceptionHandler;

    public JudgeException() {
        this.exceptionHandler = ResultHandler.SERVER_ERROR;
    }

    public ExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }


    public JudgeException(ExceptionHandler handler, ExceptionHandler exceptionHandler) {
        super(handler.getMessage());
        this.exceptionHandler = exceptionHandler;
    }

    public JudgeException(ExceptionHandler handler, Throwable cause) {
        super(handler.getMessage(), cause);
        this.exceptionHandler = handler;
    }

    public JudgeException(String message) {
        super(message);
        this.exceptionHandler = ResultHandler.SERVER_ERROR;
    }

    public JudgeException(String message, Throwable cause) {
        super(message, cause);
        this.exceptionHandler = ResultHandler.SERVER_ERROR;
    }
}
