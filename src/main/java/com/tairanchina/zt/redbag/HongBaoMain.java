package com.tairanchina.zt.redbag;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.JedisCluster;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class HongBaoMain {
    private static final Integer threadCount = 20;
    private static final Integer hongBaoNum = 100;
    private static final String hongBaoPoolKey = "{x}" + "hongBaoPoolKey" ;
    private static final String hongBaoDetailListKey = "{x}" + "hongBaoDetailListKey";
    private static final String userIdRecordKey = "{x}" + "userIdRecordKey";

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    private static final String script = "if redis.call('hexists',KEYS[3],KEYS[4]) ~= 0 then\n" +
            "    return nil\n" +
            "else\n" +
            "    local hongbao = redis.call('rpop',KEYS[1]);\n" +
            "    if hongbao then\n" +
            "        local x = cjson.decode(hongbao);\n" +
            "        x['userId'] = KEYS[4];\n" +
            "        local re = cjson.encode(x);\n" +
            "        redis.call('hset',KEYS[3],KEYS[4],'1');\n" +
            "        redis.call('lpush',KEYS[2],re);\n" +
            "        return re;\n" +
            "    end\n" +
            "end\n" +
            "return nil;";

    public static void main(String[] args) throws Exception {
        hongBaoInit();
        getHongBao();

    }

    public static void hongBaoInit() throws Exception {
        JedisCluster jedis = RedisClusterUtil.getJedisCluster();
        jedis.del(hongBaoPoolKey, hongBaoDetailListKey, userIdRecordKey);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            new Thread(() -> {
                JedisCluster jedis1 = RedisClusterUtil.getJedisCluster();
                int per = hongBaoNum / threadCount;
                JSONObject jsonObject = new JSONObject();
                for (int j = index * per; j < (index + 1) * per; j++) {
                    jsonObject.put("id", "rid_" + j);
                    jsonObject.put("money", j);
                    jedis1.lpush(hongBaoPoolKey, jsonObject.toJSONString());
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
    }


    public static void getHongBao() throws Exception {

        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                JedisCluster jedis = RedisClusterUtil.getJedisCluster();
                while (true) {
                    String userId = "{x}" + UUID.randomUUID().toString();
                    Object object = jedis.eval(script, 4, hongBaoPoolKey, hongBaoDetailListKey, userIdRecordKey, userId);
                    if (null != object) {
                        System.out.println("用户id:" + userId + " 抢到的红包:" + object);
                    } else {
                        if (jedis.llen(hongBaoPoolKey) == 0) {
                            break;
                        }
                    }
                }
                countDownLatch.countDown();

            }).start();
        }
        countDownLatch.await();
    }
}


