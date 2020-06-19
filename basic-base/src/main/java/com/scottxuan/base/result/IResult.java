package com.scottxuan.base.result;

import com.scottxuan.base.error.IError;

/**
 * @author : zhaoxuan
 */
public interface IResult<T> {
    T getValue();

    IError getError();

    Object[] getArgs();

    boolean isSuccess();
}
