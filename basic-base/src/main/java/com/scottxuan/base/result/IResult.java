package com.scottxuan.base.result;

import com.scottxuan.base.error.IError;

/**
 * @author : zhaoxuan
 */
public interface IResult<T> {
    /**
     * 结果值
     *
     * @return
     */
    T getValue();

    /**
     * 结果错误体
     *
     * @return
     */
    IError getError();

    /**
     * 错误体参数
     *
     * @return
     */
    Object[] getArgs();

    /**
     * 是否操作成功
     *
     * @return
     */
    boolean isSuccess();
}
