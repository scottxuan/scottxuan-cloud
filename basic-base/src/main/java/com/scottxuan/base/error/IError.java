package com.scottxuan.base.error;

/**
 * @author : scottxuan
 */
public interface IError {
    /**
     * 错误编码
     *
     * @return
     */
    int getCode();

    /**
     * 错误信息
     *
     * @return
     */
    String getMessage();
}
