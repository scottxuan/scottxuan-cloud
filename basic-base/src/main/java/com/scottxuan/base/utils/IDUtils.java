package com.scottxuan.base.utils;

import com.scottxuan.base.uuid.SnowflakeIdWorker;

import java.net.InetAddress;
import java.util.Random;

/**
 * @author : scottxuan
 */
public class IDUtils {

    private static SnowflakeIdWorker worker = null;

    public static String generatorId() {
        return String.valueOf(getLongUuid());
    }

    /**
     * 单例锁
     */
    private static final Object WORKER_LOCK = new Object();

    private static SnowflakeIdWorker getSnowflakeIdWorker() {
        if (worker == null) {
            synchronized (WORKER_LOCK) {
                if (worker == null) {
                    //serverId和workerId的范围 [0,32)
                    int serverId = getServerId();
                    int workerId = new Random().nextInt(32);
                    worker = new SnowflakeIdWorker(workerId, serverId);
                }
            }
        }
        return worker;
    }

    private static int getServerId() {
        try {
            //服务器集群中IP地址一般是连续的，所以取IP地址最后5位，基本可以保证serverId不会重复
            byte[] bytes = InetAddress.getLocalHost().getAddress();
            byte byta = bytes[3];
            int num = ((byta & 0x1F) & 0xFF); //取IP地址的最后5位bit
            return num;
        } catch (Exception ex) {
            //获取不到IP，取随机码
            return new Random().nextInt(32);
        }
    }

    private static long getLongUuid() {
        return getSnowflakeIdWorker().nextId();
    }
}
