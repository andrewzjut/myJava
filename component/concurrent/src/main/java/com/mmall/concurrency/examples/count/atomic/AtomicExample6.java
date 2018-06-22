package com.mmall.concurrency.examples.count.atomic;

import com.mmall.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 演示并发 AtomicBoolean
 */
@ThreadSafe
@Slf4j
public class AtomicExample6 {

    private static AtomicBoolean isHappend = new AtomicBoolean(false);

    //请求总数
    private static int clientTotal = 5000;
    //并发数
    private static int threadTotal = 200;

    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    test();
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("is happened:{},count={}", isHappend.get(), count);
    }

    private static void test() {
        if (isHappend.compareAndSet(false, true)) {
            count.getAndIncrement();
            //只执行一次
            log.info("execute");
        }else {
            log.info("不加一");
        }
    }

}
