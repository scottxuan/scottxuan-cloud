package com.scottxuan.core.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key cannot be empty.");
        }
        return cacheService.redisTemplate.opsForValue().get(key);
    }

    public static void set(String key, Object value) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key cannot be empty.");
        }
        cacheService.redisTemplate.opsForValue().set(key, value);
    }

    public static void set(String key, Object value, long timeout) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key cannot be empty.");
        }
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout cannot be less than zero.");
        }
        cacheService.redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    public static void setTimeOut(String key, long timeout) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key cannot be empty.");
        }
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout cannot be less than zero.");
        }
        cacheService.redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    public static void setHash(String key, String valueKey, Object value) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key cannot be empty.");
        }
        if (StringUtils.isEmpty(valueKey)) {
            throw new IllegalArgumentException("valueKey cannot be empty.");
        }
        cacheService.redisTemplate.opsForHash().put(key, valueKey, value);
    }

    public static Object getHash(String key, String valueKey) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key cannot be empty.");
        }
        return cacheService.redisTemplate.opsForHash().get(key, valueKey);
    }

    public static List<Object> getHashValues(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key cannot be empty.");
        }
        return cacheService.redisTemplate.opsForHash().values(key);
    }

    public static Set<Object> getHashKeys(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key cannot be empty.");
        }
        return cacheService.redisTemplate.opsForHash().keys(key);
    }

    public static Map<Object, Object> getHashAll(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key cannot be empty.");
        }
        return cacheService.redisTemplate.opsForHash().entries(key);
    }

    public static Long deleteHash(String key, String... valueKeys) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key cannot be empty.");
        }
        if (valueKeys != null && valueKeys.length > 0) {
            return cacheService.redisTemplate.opsForHash().delete(key, valueKeys);
        }
        return 0L;
    }

    public static Boolean containKey(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key cannot be empty.");
        }
        return cacheService.redisTemplate.hasKey(key);
    }

    public static Boolean containHashKey(String key, String valueKey) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key cannot be empty.");
        }
        if (StringUtils.isEmpty(valueKey)) {
            throw new IllegalArgumentException("valueKey cannot be empty.");
        }
        return cacheService.redisTemplate.opsForHash().hasKey(key, valueKey);
    }

    public static void delete(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key cannot be empty.");
        }
        cacheService.redisTemplate.delete(key);
    }

    public static void delete(List<String> keys) {
        if (!CollectionUtils.isEmpty(keys)) {
            cacheService.redisTemplate.delete(keys);
        }
    }

    public static Set<String> keys(String key) {
        return cacheService.redisTemplate.keys(key);
    }

    public static Long clear(){
        Set<String> keys = cacheService.redisTemplate.keys("*");
        return cacheService.redisTemplate.delete(keys);
    }

    public static Long getExpireTime(String key) {
        return cacheService.redisTemplate.getExpire(key);
    }
}
