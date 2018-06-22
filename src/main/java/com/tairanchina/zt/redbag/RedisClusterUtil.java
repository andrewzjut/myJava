package com.tairanchina.zt.redbag;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class RedisClusterUtil {
    private static JedisCluster jedisCluster = null;


    public static JedisCluster getJedisCluster() {
        if (null == jedisCluster) {
            String[] serverArray = "127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005 127.0.0.1:7000".split("\\s+");
            Set<HostAndPort> nodes = new HashSet<>();

            for (String ipPort : serverArray) {
                String[] ipPortPair = ipPort.split(":");
                nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
            }

            //注意：这里超时时间不要太短，他会有超时重试机制。而且其他像httpclient、dubbo等RPC框架也要注意这点
            jedisCluster = new JedisCluster(nodes, 1000, 1000, 1, new GenericObjectPoolConfig());

        }
        return jedisCluster;
    }

    public static void main(String[] args) {
        JedisCluster cluster = getJedisCluster();
        cluster.set("a","xxx");
        System.out.println(cluster.get("a"));
    }
}
