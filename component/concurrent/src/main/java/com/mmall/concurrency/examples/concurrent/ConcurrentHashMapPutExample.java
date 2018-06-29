package com.mmall.concurrency.examples.concurrent;

import com.mmall.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 常见线程安全的Collections
 */
@Slf4j
@ThreadSafe
public class ConcurrentHashMapPutExample {
    //请求总数
    private static int clientTotal = 5000;
    //并发数
    private static int threadTotal = 200;

    private static ConcurrentHashMap<String, Long> lastOffset = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            final int index = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    put("topicPartition", (long) index);
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        long end = System.currentTimeMillis();
        log.info("lastOffset:{} , time:{}", lastOffset, end - start);

    }

    public static void put(String topicPartition, Long offset) {
        log.info("topicPartition:{} , offset:{}", topicPartition, offset);
        lastOffset.compute(topicPartition, (String __, Long old) -> {
            if (old == null) return offset;
            else return Math.max(offset, old);
        });
    }

}
