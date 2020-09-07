package com.scottxuan.core.http;

/**
 * @author scottxuan
 */
public interface HttpRequest{

    /**
     * 请求url
     * @return
     */
    String getUrl();

    /**
     * 请求method
     * @return
     */
    HttpMethod getMethod();

    /**
     * 请求参数
     * @return
     */
    String getParams();

    /**
     * 参数类型
     * @return
     */
    ParamType getParamType();

    /**
     * 响应接收entity
     * @return
     */
    Class getResponse();

}
