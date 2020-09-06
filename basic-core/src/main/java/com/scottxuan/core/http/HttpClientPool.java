package com.scottxuan.core.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

@Slf4j
public class HttpClientPool {
    /**
     * 最大连接数
     */
    private static final int DEFAULT_POOL_MAX_TOTAL = 200;

    /**
     * 每个路由的默认最大连接数
     */
    private static final int DEFAULT_POOL_MAX_PER_ROUTE = 50;

    /**
     * 请求重试次数
     */
    private static final int RETRY_TIMES = 3;

    /**
     * 连接池管理类
     */
    private static PoolingHttpClientConnectionManager httpClientManager;

    /**
     * 发送请求的客户端单例
     */
    private static volatile CloseableHttpClient httpClient;

    /**
     * 发送请求的客户端单例
     */
    private static volatile CloseableHttpClient httpClientProxy;

    /**
     * 锁
     */
    private static final Object LOCK = new Object();

    private static HttpClientPoolMonitor httpClientPoolMonitor;

    static {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        httpClientManager = new PoolingHttpClientConnectionManager(registry);
        httpClientPoolMonitor = new HttpClientPoolMonitor(httpClientManager);
    }

    public static CloseableHttpClient getHttpClient() {
        if (httpClient == null) {
            synchronized (LOCK) {
                if (httpClient == null) {
                    httpClient = createHttpClient(null);
                }
            }
        }
        return httpClient;
    }

    public static CloseableHttpClient getHttpClientProxy(HttpHost httpProxy) {
        if (httpClientProxy == null) {
            synchronized (LOCK) {
                if (httpClientProxy == null) {
                    httpClientProxy = createHttpClient(httpProxy);
                }
            }
        }
        return httpClient;
    }

    public static CloseableHttpClient createHttpClient(HttpHost httpProxy) {

        httpClientManager.setMaxTotal(DEFAULT_POOL_MAX_TOTAL);
        httpClientManager.setDefaultMaxPerRoute(DEFAULT_POOL_MAX_PER_ROUTE);
        HttpRequestRetryHandler handler = (e, i, httpContext) -> {
            if (i > RETRY_TIMES) {
                log.error("retry has more than 3 time, give up request");
                return false;
            }
            if (e instanceof NoHttpResponseException) {
                // 服务器没有响应
                log.error("receive no response from server, retry");
                return false;
            }
            if (e instanceof SSLHandshakeException) {
                // SSL 握手异常
                log.error("SSL hand shake exception");
                return false;
            }
            if (e instanceof InterruptedIOException) {
                // IO 中断
                log.error("InterruptedIOException");
                return false;
            }
            if (e instanceof UnknownHostException) {
                // 服务器不可达
                log.error("server host unknown");
                return false;
            }
            if (e instanceof ConnectTimeoutException) {
                // 连接超时
                log.error("Connection Timeout");
                return false;
            }
            if (e instanceof SSLException) {
                log.error("SSLException");
                return false;
            }
            HttpClientContext context = HttpClientContext.adapt(httpContext);
            HttpRequest request = context.getRequest();
            return !(request instanceof HttpEntityEnclosingRequest);
        };

        DefaultProxyRoutePlanner routePlanner = null;

        if (httpProxy != null) {
            new DefaultProxyRoutePlanner(httpProxy);
        }

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(httpClientManager)
                .setRetryHandler(handler)
                .setRoutePlanner(routePlanner)
                .build();
        httpClientPoolMonitor.start();
        return httpClient;
    }
}

