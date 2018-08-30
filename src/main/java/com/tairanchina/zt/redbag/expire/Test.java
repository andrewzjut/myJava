package com.tairanchina.zt.redbag.expire;

import com.tairanchina.zt.redbag.RedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.List;

import static com.tairanchina.zt.redbag.RedisClusterUtil.getJedisCluster;

public class Test {
    public static void main(String[] args) {

//        RedisUtils.init(null);
//        Jedis jedis = RedisUtils.getResource();


        JedisCluster jedis = getJedisCluster();
//        config(jedis);//建议在redis配置文件中设置

        jedis.psubscribe(new KeyExpiredListener(), "__keyevent@0__:expired");



    }

    private static void config(Jedis jedis){
        String parameter = "notify-keyspace-events";
        List<String> notify = jedis.configGet(parameter);
        if(notify.get(1).equals("")){
            jedis.configSet(parameter, "Ex"); //过期事件
        }
    }
}
