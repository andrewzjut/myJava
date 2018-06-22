package com.mmall.concurrency.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CountDownLatchExample2 {
    private final static int threadCount = 200;

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
//            Thread.sleep(1);
            executorService.execute(() -> {
                try {
                    test(threadNum);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        //主线程只等待10 ms  超时的话 执行main线程
        countDownLatch.await(10, TimeUnit.MILLISECONDS);
        log.info("结束");
        executorService.shutdown();
    }

    private static void test(int threadNum) throws Exception {
        Thread.sleep(11);
        log.info("{}", threadNum);
    }
}
