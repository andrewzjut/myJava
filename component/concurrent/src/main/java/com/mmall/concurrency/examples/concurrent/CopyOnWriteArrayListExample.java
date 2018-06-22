package com.mmall.concurrency.examples.concurrent;

import com.mmall.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 常见线程安全的Collections
 */
@Slf4j
@ThreadSafe
public class CopyOnWriteArrayListExample {
    //请求总数
    private static int clientTotal = 5000;
    //并发数
    private static int threadTotal = 200;
    //线程不安全
    private static CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();


    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            final int count = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    update(count);
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("copyOnWriteArrayList:{}", copyOnWriteArrayList.size());


    }

    private static void update(int i) {
        copyOnWriteArrayList.add(i);
    }
}
