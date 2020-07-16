package com.scottxuan.base.error;

import java.io.Serializable;

/**
 * @author : scottxuan
 */
public interface IError extends Serializable {
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
