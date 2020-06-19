package com.scottxuan.base.exception;

import lombok.Getter;
import com.scottxuan.base.error.IError;

/**
 * @author : zhaoxuan
 */
@Getter
public class BizException extends RuntimeException{

    private int errorCode;
    private String errorMsgKey;
    private Object[] args;

    public BizException(IError error) {
        super(String.valueOf(error.getCode()));
        this.errorCode = error.getCode();
        this.errorMsgKey = error.getMessage();
    }

    public BizException(IError error, Object... args) {
        super(String.valueOf(error.getCode()));
        this.errorCode = error.getCode();
        this.errorMsgKey = error.getMessage();
        this.args = args;
    }

    @Override
    public String getMessage() {
        return this.errorMsgKey;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
