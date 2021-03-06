package com.scottxuan.core.http;

/**
 * @author scottxuan
 */
public interface HttpUrl {
    /**
     * Http请求方式
     * @return
     */
    HttpMethod method();

    /**
     * 请求url
     * @return
     */
    String url();
}
