package com.scottxuan.core.http;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.IOException;
import java.util.Map;

public class HttpClient {
    /**
     * 默认建立连接超时时间
     */
    private static final int DEFAULT_CONNECT_TIMEOUT = 5 * 1000;

    /**
     * 默认从连接池取出连接的超时时间
     */
    private static final int DEFAULT_CONNECT_REQUEST_TIMEOUT = 2 * 1000;

    /**
     * 默认数据读取超时时间
     */
    private static final int DEFAULT_SOCKET_TIMEOUT = 60 * 1000;

    /**
     * 最大重定向次数
     */
    private static final int MAX_REDIRECTS = 3;

    /**
     * 编码
     */
    private static final String CHARSET = "UTF-8";

    /**
     * 初始化response解析器
     */
    private static final ResponseHandler<String> RESPONSE_HANDLER;

    static {
        RESPONSE_HANDLER = new BasicResponseHandler();
    }

    public static String execute(String url, String params, ParamType paramType, HttpMethod method) {
        if (HttpMethod.GET == method) {
            return get(url, null, null, paramType);
        }
        if (HttpMethod.POST == method) {
            return post(url, params, null, null, paramType);
        }
        return "";
    }

    public static String execute(String url, String params, Map<String, String> headers, Integer timeOut, ParamType paramType, HttpMethod method) {
        if (HttpMethod.GET == method) {
            return get(url, headers, timeOut, paramType);
        }
        if (HttpMethod.POST == method) {
            return post(url, params, headers, timeOut, paramType);
        }
        return "";
    }

    public static String get(String url) {
        return get(url, null, null, ParamType.JSON);
    }

    public static String get(String url, ParamType paramType) {
        return get(url, null, null, paramType);
    }

    public static String get(String url, Integer timeOut) {
        return get(url, null, timeOut, ParamType.JSON);
    }

    public static String get(String url, Integer timeOut, ParamType paramType) {
        return get(url, null, timeOut, paramType);
    }

    public static String get(String url, Map<String, String> headers, Integer timeOut, ParamType paramType) {
        HttpGet httpGet = new HttpGet(url);
        config(httpGet, timeOut, paramType);
        configHeader(httpGet, headers);
        return request(httpGet);
    }

    public static String post(String url, String params) {
        return post(url, params, null, null, ParamType.JSON);
    }

    public static String post(String url, String params, ParamType paramType) {
        return post(url, params, null, null, paramType);
    }

    public static String post(String url, String params, Integer timeOut) {
        return post(url, params, null, timeOut, ParamType.JSON);
    }

    public static String post(String url, String params, Integer timeOut, ParamType paramType) {
        return post(url, params, null, timeOut, paramType);
    }

    public static String post(String url, String params, Map<String, String> headers, Integer timeOut, ParamType paramType) {
        StringEntity postEntity = new StringEntity(params, CHARSET);
        HttpPost httpPost = new HttpPost(url);
        config(httpPost, timeOut, paramType);
        configHeader(httpPost, headers);
        httpPost.setEntity(postEntity);
        return request(httpPost);
    }

    private static void config(HttpRequestBase httpRequestBase, Integer timeOut, ParamType paramType) {
        RequestConfig config = RequestConfig
                .custom()
                .setConnectTimeout((timeOut == null || timeOut <= 0) ? DEFAULT_CONNECT_TIMEOUT : timeOut)
                .setSocketTimeout((timeOut == null || timeOut <= 0) ? DEFAULT_SOCKET_TIMEOUT : timeOut)
                .setConnectionRequestTimeout(DEFAULT_CONNECT_REQUEST_TIMEOUT)
                .setCircularRedirectsAllowed(true)
                .setMaxRedirects(MAX_REDIRECTS)
                .build();
        httpRequestBase.setConfig(config);
        if (ParamType.JSON == paramType) {
            httpRequestBase.addHeader("Content-Type", "application/json; charset=utf-8");
        }
        if (ParamType.XML == paramType) {
            httpRequestBase.addHeader("Content-Type", "application/xml; charset=utf-8");
        }
    }

    private static void configHeader(HttpRequestBase httpRequestBase, Map<String, String> headers) {
        if (!headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpRequestBase.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    private static String request(HttpRequestBase request) {
        try {
            return HttpClientPool.getHttpClient().execute(request, RESPONSE_HANDLER);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
