package com.scottxuan.core.mybatis;


/**
 * @author : zhaoxuan
 */
public interface MapperCallBack {
    /**
     * redis缓存
     *
     * @return
     */
    Object doWithRedis();
}
