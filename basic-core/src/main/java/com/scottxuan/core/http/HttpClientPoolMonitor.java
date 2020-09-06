package com.scottxuan.core.http;

import org.apache.http.conn.HttpClientConnectionManager;

import java.util.concurrent.TimeUnit;

/**
 * @author scottxuan
 */
public class HttpClientPoolMonitor extends Thread {
    private final HttpClientConnectionManager connectionManager;
    private volatile boolean exitFlag = false;

    public HttpClientPoolMonitor(HttpClientConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (!this.exitFlag) {
            synchronized (this) {
                try {
                    this.wait(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 关闭失效的连接
            connectionManager.closeExpiredConnections();
            // 可选的, 关闭30秒内不活动的连接
            connectionManager.closeIdleConnections(30, TimeUnit.SECONDS);
        }
    }

    public void shutdown() {
        this.exitFlag = true;
        synchronized (this) {
            notify();
        }
    }
}
