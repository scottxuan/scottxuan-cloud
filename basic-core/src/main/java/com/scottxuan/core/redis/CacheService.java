package com.scottxuan.core.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author : zhaoxuan
 */
@Component
public class CacheService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private static CacheService cacheService;

    @PostConstruct
    public void init() {
        cacheService = this;
    }

    public static Object get(String key) {
        return cacheService.redisTemplate.opsForValue().get(key);
    }

    public static void set(String key, Object value) {
        cacheService.redisTemplate.opsForValue().set(key, value);
    }

    public static void set(String key, Object value, long timeout) {
        cacheService.redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    public static void setTimeOut(String key, long timeout) {
        cacheService.redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    public static void setHash(String key, String valueKey, Object value) {
        cacheService.redisTemplate.opsForHash().put(key, valueKey, value);
    }

    public static Object getHash(String key, String valueKey) {
        return cacheService.redisTemplate.opsForHash().get(key, valueKey);
    }

    public static List<Object> getHashValues(String key) {
        return cacheService.redisTemplate.opsForHash().values(key);
    }

    public static Set<Object> getHashKeys(String key) {
        return cacheService.redisTemplate.opsForHash().keys(key);
    }

    public static Map<Object, Object> getHashAll(String key) {
        return cacheService.redisTemplate.opsForHash().entries(key);
    }

    public static Long deleteHash(String key, String... valueKeys) {
        if (valueKeys != null && valueKeys.length > 0) {
            return cacheService.redisTemplate.opsForHash().delete(key, (Object) valueKeys);
        }
        return 0L;
    }

    public static Boolean containKey(String key) {
        return cacheService.redisTemplate.hasKey(key);
    }

    public static Boolean containHashKey(String key, String valueKey) {
        if (StringUtils.isEmpty(valueKey)) {
            throw new IllegalArgumentException("valueKey cannot be empty.");
        }
        return cacheService.redisTemplate.opsForHash().hasKey(key, valueKey);
    }

    public static Boolean delete(String key) {
        return cacheService.redisTemplate.delete(key);
    }

    public static void delete(List<String> keys) {
        cacheService.redisTemplate.delete(keys);
    }

    public static Set<String> keys(String key) {
        return cacheService.redisTemplate.keys(key);
    }

    public static Long clear() {
        Set<String> keys = cacheService.redisTemplate.keys("*");
        if (keys!=null && !keys.isEmpty()) {
            return cacheService.redisTemplate.delete(keys);
        }
        return 0L;
    }

    public static Long clear(String prefix) {
        Set<String> keys = cacheService.redisTemplate.keys(prefix + "*");
        if (keys!=null && !keys.isEmpty()) {
            return cacheService.redisTemplate.delete(keys);
        }
        return 0L;
    }

    public static Long getExpireTime(String key) {
        return cacheService.redisTemplate.getExpire(key);
    }

    public static boolean lock(String key, int timeout) {
        String threadId = String.valueOf(Thread.currentThread().getId());
        if (containKey(key)) {
            if (threadId.equals(cacheService.redisTemplate.opsForValue().get(key))) {
                return Boolean.TRUE;
            }
        }
        return cacheService.redisTemplate.opsForValue().setIfAbsent(key, threadId, timeout, TimeUnit.SECONDS);
    }

    public static boolean unlock(String key) {
        return !containKey(key) || delete(key);
    }
}
