package com.mmall.concurrency.examples.count.atomic;

import com.mmall.concurrency.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 演示并发 AtomicLong
 */
@ThreadSafe
public class AtomicExample3 {

    //请求总数
    private static int clientTotal = 5000;
    //并发数
    private static int threadTotal = 200;
    private static AtomicLong count = new AtomicLong(0);
    private static Logger logger = LoggerFactory.getLogger(AtomicExample3.class);

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        logger.info("count:{}", count.get());
    }

    private static void add() {
        count.incrementAndGet();
    }
}
