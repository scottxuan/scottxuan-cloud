package com.scottxuan.base.exception;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * @author : pc
 * @date : 2020/8/19
 */
public class Assert {
    public static void isTrue(boolean var1, String var2) {
        if (!var1) {
            throw new IllegalArgumentException(var2);
        }
    }

    public static void isNull(Object object, String var2) {
        if (object == null) {
            throw new IllegalArgumentException(var2);
        }
    }

    public static void isBlank(String var1, String var2) {
        if (StringUtils.isBlank(var1)) {
            throw new IllegalArgumentException(var2);
        }
    }

    public static void isEmpty(Collection<?> var1, String var2) {
        if (var1 == null || var1.isEmpty()) {
            throw new IllegalArgumentException(var2);
        }
    }
}
