package com.tairanchina.zt.redbag;

import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisLock {
    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * 释放分布式锁
     *
     * @param jedis     Redis客户端
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean unlock(Jedis jedis, String lockKey, String requestId) {

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }


    private static final Long LOCK_SUCCESS = 1l;
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    /**
     * 尝试获取分布式锁
     *
     * @param jedis      Redis客户端
     * @param lockKey    锁
     * @param requestId  请求标识
     * @return 是否获取成功
     */
    public static boolean lock(Jedis jedis, String lockKey, String requestId) {


        String script = "local resp = 0;\n" +
                "local result = redis.call('set', KEYS[1], ARGV[1],  ARGV[2],  ARGV[3],  200000);\n" +
                "if result then\n" +
                "    resp = 1\n" +
                "    return resp\n" +
                "else\n" +
                "    if redis.call('get', KEYS[1]) == ARGV[1] then\n" +
                "        resp = 1\n" +
                "    else resp = 0\n" +
                "    end\n" +
                "end\n" +
                "return resp";

        Object result = jedis.eval(script,Collections.singletonList(lockKey), Arrays.asList(requestId,SET_IF_NOT_EXIST,SET_WITH_EXPIRE_TIME));
        if (LOCK_SUCCESS == result) {
            return true;
        }
        return false;
    }

    private static int count = 0;
    private static int threadNum = 10;
    public static void main(String[] args) throws Exception {
        Jedis jedis1 = RedisUtils.getResource();
        jedis1.flushAll();
        jedis1.close();
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            final int index = i;
            executorService.execute(() -> {
                Jedis jedis = RedisUtils.getResource();

                String requestId = UUID.randomUUID().toString();

                while (true){
                    if (lock(jedis, "lock", requestId)) {
                        System.out.println("线程：" + index + " 获取锁成功");
                        add();
                        if (unlock(jedis, "lock", requestId)) {
                            System.out.println("线程：" + index + " 释放锁成功");
                            System.out.println();
                            jedis.close();
                            break;
                        } else {
                            System.out.println("线程：" + index + " 释放锁失败");
                        }
                        break;
                    }
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println(count);
    }

    public static void add() {
        count++;
    }
}
