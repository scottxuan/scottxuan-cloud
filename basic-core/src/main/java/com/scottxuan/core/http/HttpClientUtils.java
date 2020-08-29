package com.scottxuan.core.http;

import com.scottxuan.base.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;


/**
 * @author : scottxuan
 */
@Slf4j
public class HttpClientUtils {

    /**
     * 与服务器连接超时时间
     */
    private static final int CONNECT_TIMEOUT = 10 * 1000;

    /**
     * socket 读取数据时间
     */
    private static final int SOCKET_TIMEOUT = 10 * 1000;

    /**
     * 连接请求超时时间
     */
    private static final int CONNECT_REQUEST_TIMEOUT = 10 * 1000;

    /**
     * 最大重定向次数
     */
    private static final int MAX_REDIRECTS = 3;

    private static final String CHAR_SET = "utf-8";


    /**
     * @param url 请求url
     * @return
     */
    public static String doGet(String url) {
        return doGetProxy(url, null, null, null, null);
    }

    /**
     * @param url    url地址
     * @param params 请求参数
     * @return
     */
    public static String doGet(String url, Map<String, String> params) {
        return doGetProxy(url, params, null, null, null);
    }

    /**
     * @param url       url地址
     * @param proxyHost 代理服务器
     * @param proxyPort 代理端口号
     * @return
     */
    public static String doGetProxy(String url, String proxyHost, Integer proxyPort) {
        return doGetProxy(url, null, proxyHost, proxyPort, HttpProtocol.HTTP);
    }

    /**
     * @param url       url地址
     * @param params    请求参数
     * @param proxyHost 代理服务器
     * @param proxyPort 代理端口号
     * @return
     */
    public static String doGetProxy(String url, Map<String, String> params, String proxyHost, Integer proxyPort) {
        return doGetProxy(url, params, proxyHost, proxyPort, HttpProtocol.HTTP);
    }

    /**
     * @param url       url地址
     * @param params    请求参数
     * @param proxyHost 代理服务器
     * @param proxyPort 代理端口号
     * @param httpEnum  协议:HTTP / HTTPS
     * @return
     */
    public static String doGetProxy(String url, Map<String, String> params, String proxyHost, Integer proxyPort, HttpProtocol httpEnum) {
        CloseableHttpClient httpClient;
        /**
         * 代理信息处理
         */
        if (proxyHost != null && proxyPort != null && httpEnum != null) {
            httpClient = HttpClientPoolUtils.getHttpClient(new HttpHost(proxyHost, proxyPort, httpEnum.getValue()));
        } else {
            httpClient = HttpClientPoolUtils.getHttpClient();
        }
        /**
         * params 参数处理
         */
        if (null != params && !params.isEmpty()) {
            StringBuilder urlBuilder = new StringBuilder(url);
            params.forEach((k, v) -> {
                if (StringUtils.isNotEmpty(k) && ObjectUtils.isNotEmpty(v)) {
                    urlBuilder.append("&").append(k.trim()).append("=").append(v.toString().trim());
                }
            });
            if (url.indexOf("?") > 0) {
                url += urlBuilder.toString();
            } else {
                url += ("?" + urlBuilder.substring(1));
            }
        }

        HttpGet httpGet = new HttpGet(url);
        configJson(httpGet);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, CHAR_SET);
            }
        } catch (IOException e) {
            log.error("HttpClient Get 请求异常: " + e);
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("HttpResponse 连接释放异常: " + e);
                }
            }
        }
        return null;
    }

    public static String doPost(String url,String body) {
        return doPostProxy(url,body, null, null, HttpProtocol.HTTP);
    }

    public static String doPost(String url,String body, String proxyHost, Integer proxyPort) {
        return doPostProxy(url,body, proxyHost, proxyPort, HttpProtocol.HTTP);
    }

    public static String doPost(String url,String body,HttpProtocol httpEnum) {
        return doPostProxy(url,body, null, null, httpEnum);
    }

    public static String doPostProxy(String url,String body, String proxyHost, Integer proxyPort, HttpProtocol httpEnum) {
        CloseableHttpClient httpClient;
        /**
         * 代理信息处理
         */
        if (proxyHost != null && proxyPort != null && httpEnum != null) {
            httpClient = HttpClientPoolUtils.getHttpClient(new HttpHost(proxyHost, proxyPort, httpEnum.getValue()));
        } else {
            httpClient = HttpClientPoolUtils.getHttpClient();
        }

        HttpPost httpPost = new HttpPost(url);
        configJson(httpPost);
        StringEntity postEntity = new StringEntity(body, CHAR_SET);
        httpPost.setEntity(postEntity);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, CHAR_SET);
            }
        } catch (IOException e) {
            log.error("HttpClient Post 请求异常: " + e);
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("HttpResponse连接释放异常: " + e);
                }
            }
        }
        return null;
    }

    private static void configJson(HttpRequestBase httpRequestBase) {
        commonConfig(httpRequestBase);
        httpRequestBase.addHeader("Content-Type", "application/json; charset=utf-8");
    }

    private static void commonConfig(HttpRequestBase httpRequestBase) {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setCircularRedirectsAllowed(true)
                .setMaxRedirects(MAX_REDIRECTS)
                .setConnectionRequestTimeout(CONNECT_REQUEST_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpRequestBase.setConfig(config);
    }

}
