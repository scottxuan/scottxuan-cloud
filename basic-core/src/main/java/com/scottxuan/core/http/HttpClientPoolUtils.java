package com.scottxuan.core.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
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

/**
 * @author : scottxuan
 */
@Slf4j
public class HttpClientPoolUtils {

    /**
     * 最大连接数
     */
    private static int MAX_TOTAL = 200;

    /**
     * 每个路由的默认最大连接数
     */
    private static int MAX_ROUTE = 50;

    /**
     * 不活跃多长时间后需要进行一次验证
     */
    private static int VALIDATE_AFTER_INACTIVITY = 10 * 1000;

    /**
     * 请求重试次数
     */
    private static final int RETRY_TIMES = 3;

    /**
     * 发送请求的客户端单例
     */
    private static CloseableHttpClient httpClient;
    /**
     * 连接池管理类
     */
    private static PoolingHttpClientConnectionManager manager;

    /**
     * 请求重试处理程序机制
     */
    private static HttpRequestRetryHandler retryHandler;

    /**
     * 单例锁
     */
    private static final Object SYNC_LOCK = new Object();


    /**
     * 获取httpclient连接
     * @return
     */
    public static CloseableHttpClient getHttpClient() {
        if (httpClient == null) {
            synchronized (SYNC_LOCK) {
                if (httpClient == null) {
                    httpClient = createHttpClient();
                }
            }
        }
        return httpClient;
    }

    /**
     * 获取httpclient连接,含代理
     * @return
     */
    public static CloseableHttpClient getHttpClient(HttpHost httpProxy) {
        if (httpClient == null) {
            synchronized (SYNC_LOCK) {
                if (httpClient == null) {
                    httpClient = createHttpClient(httpProxy);
                }
            }
        }
        return httpClient;
    }

    /**
     * 创建httpclient连接
     * @return
     */
    private static CloseableHttpClient createHttpClient(HttpHost httpProxy) {
        createHttpManager();
        configHttpManager();
        configRetryHandler();
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(httpProxy);
        return HttpClients.custom().setConnectionManager(manager).setRetryHandler(retryHandler).setRoutePlanner(routePlanner).build();
    }

    /**
     * 创建httpclient连接
     * @return
     */
    private static CloseableHttpClient createHttpClient() {
        createHttpManager();
        configHttpManager();
        configRetryHandler();
        return HttpClients.custom().setConnectionManager(manager).setRetryHandler(retryHandler).build();
    }

    /**
     * 与特定协议关联定制创建连接管理器
     */
    private static void createHttpManager() {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register(HttpProtocol.HTTP.getValue(), plainsf)
                .register(HttpProtocol.HTTPS.getValue(), sslsf)
                .build();
        manager = new PoolingHttpClientConnectionManager(registry);
    }

    /**
     * 连接管理器配置
     */
    private static void configHttpManager() {
        manager.setValidateAfterInactivity(VALIDATE_AFTER_INACTIVITY);
        manager.setMaxTotal(MAX_TOTAL);
        manager.setDefaultMaxPerRoute(MAX_ROUTE);
    }

    private static void configRetryHandler(){
        retryHandler = (e, i, httpContext) -> {
            if (i > RETRY_TIMES) {
                log.error("超过最大连接次数");
                return false;
            }

            if (e instanceof NoHttpResponseException) {
                log.error("服务器没有响应");
                return false;
            }

            if (e instanceof SSLHandshakeException) {
                log.error("SSL 握手异常");
                return false;
            }

            if (e instanceof InterruptedIOException) {
                log.error("IO 中断");
                return false;
            }

            if (e instanceof UnknownHostException) {
                log.error("服务器不可达");
                return false;
            }

            if (e instanceof ConnectTimeoutException) {
                log.error("连接超时");
                return false;
            }

            if (e instanceof SSLException) {
                return false;
            }

            HttpClientContext context = HttpClientContext.adapt(httpContext);
            HttpRequest request = context.getRequest();

            return !(request instanceof HttpEntityEnclosingRequest);
        };
    }
}
