package com.scottxuan.base.exception;


import com.scottxuan.base.error.IError;

/**
 * @author : zhaoxuan
 */
public class ExceptionUtils {

    public static void throwException(IError error) {
        throw new BizException(error);
    }

    public static void throwException(IError error, Object... args) {
        if (args.length > 0) {
            throw new BizException(error, args);
        } else {
            throw new BizException(error);
        }
    }
}
