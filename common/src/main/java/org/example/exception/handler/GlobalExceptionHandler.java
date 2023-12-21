package org.example.exception.handler;

import org.example.exception.ArgsException;
import org.example.exception.JudgeException;
import org.example.exception.RemoteCalledException;
import org.example.exception.model.ResponseResult;
import org.example.exception.model.ResultHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author violet
 */
@Slf4j
@RestControllerAdvice(basePackages = {"com.topsec.judge.controller"})
public class GlobalExceptionHandler {

    @ExceptionHandler({JudgeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseResult judgeException(JudgeException judgeException) {
        if (StringUtils.isBlank(judgeException.getMessage())) {
            return ResponseResult.fail(judgeException.getExceptionHandler());
        } else {
            return ResponseResult.fail(judgeException.getExceptionHandler(), judgeException.getMessage());
        }
    }

    @ExceptionHandler(ArgsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseResult emptyException(ArgsException argsException) {
        log.error("unknown request param : {}", argsException.getMessage());
        if (StringUtils.isBlank(argsException.getMessage())) {
            return ResponseResult.fail(argsException.getResultHandler());
        } else {
            return ResponseResult.fail(argsException.getResultHandler(), argsException.getMessage());
        }
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseResult exception(Exception e) {
        log.error("未知异常!", e);
        return ResponseResult.fail(ResultHandler.UNKNOWN);
    }

    @ExceptionHandler(RemoteCalledException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseResult remoteCalledException(RemoteCalledException e) {
        log.error("远程服务:{}调用失败", e.getMessage());
        return ResponseResult.fail(e.getExceptionHandler());
    }

}
