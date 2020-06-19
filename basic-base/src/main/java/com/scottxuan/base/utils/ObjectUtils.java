package com.scottxuan.base.utils;

import org.apache.commons.lang3.exception.CloneFailedException;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * @author : scottxuan
 */
public class ObjectUtils {
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof Optional) {
            return !((Optional)obj).isPresent();
        } else if (obj instanceof CharSequence) {
            return ((CharSequence)obj).length() == 0;
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        } else if (obj instanceof Collection) {
            return ((Collection)obj).isEmpty();
        } else {
            return obj instanceof Map ? ((Map)obj).isEmpty() : false;
        }
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static <T> T defaultIfNull(T object, T defaultValue) {
        return object != null ? object : defaultValue;
    }

    public static <T> T clone(T obj) {
        if (!(obj instanceof Cloneable)) {
            return null;
        } else {
            Object result;
            if (obj.getClass().isArray()) {
                Class<?> componentType = obj.getClass().getComponentType();
                if (componentType.isPrimitive()) {
                    int length = Array.getLength(obj);
                    result = Array.newInstance(componentType, length);

                    while(length-- > 0) {
                        Array.set(result, length, Array.get(obj, length));
                    }
                } else {
                    result = ((Object[])obj).clone();
                }
            } else {
                try {
                    Method clone = obj.getClass().getMethod("clone");
                    result = clone.invoke(obj);
                } catch (NoSuchMethodException var4) {
                    throw new CloneFailedException("Cloneable type " + obj.getClass().getName() + " has no clone method", var4);
                } catch (IllegalAccessException var5) {
                    throw new CloneFailedException("Cannot clone Cloneable type " + obj.getClass().getName(), var5);
                } catch (InvocationTargetException var6) {
                    throw new CloneFailedException("Exception cloning Cloneable type " + obj.getClass().getName(), var6.getCause());
                }
            }

            return (T) result;
        }
    }
}
