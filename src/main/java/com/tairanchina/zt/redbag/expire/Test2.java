package com.tairanchina.zt.redbag.expire;

import com.tairanchina.zt.redbag.RedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import static com.tairanchina.zt.redbag.RedisClusterUtil.getJedisCluster;

public class Test2 {
    public static void main(String[] args) throws Exception {

//        RedisUtils.init(null);
//        Jedis jedis = RedisUtils.getResource();

        JedisCluster jedis = getJedisCluster();


        jedis.setex("a", 2, "222");
        Thread.sleep(2100);
        jedis.setex("a", 2, "223");
        Thread.sleep(2100);
        jedis.setex("a", 2, "224");
        Thread.sleep(2100);
        jedis.setex("a", 2, "222");
        Thread.sleep(2100);
        jedis.setex("a", 2, "222");
        Thread.sleep(2100);
    }
}
