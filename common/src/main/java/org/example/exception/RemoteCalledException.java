package org.example.exception;

import org.example.exception.handler.ExceptionHandler;
import org.example.exception.model.ResultHandler;

/**
 * @author violet
 */
public class RemoteCalledException extends RuntimeException {

    private final ExceptionHandler exceptionHandler;

    public ExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public RemoteCalledException() {
        super();
        exceptionHandler = ResultHandler.REMOTE_CALLED_ERROR;
    }

    /**
     * @param message 标明为服务名称
     */
    public RemoteCalledException(String message) {
        super(message);
        exceptionHandler = ResultHandler.REMOTE_CALLED_ERROR;
    }

    public RemoteCalledException(String message, Throwable cause) {
        super(message, cause);
        exceptionHandler = ResultHandler.REMOTE_CALLED_ERROR;
    }
}
