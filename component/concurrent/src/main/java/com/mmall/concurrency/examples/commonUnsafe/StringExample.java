package com.mmall.concurrency.examples.commonUnsafe;

import com.mmall.concurrency.examples.ConcurrencyTest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 字符串 线程安全
 */
@Slf4j
public class StringExample {
    //请求总数
    private static int clientTotal = 5000;
    //并发数
    private static int threadTotal = 200;
    //线程不安全
    private static StringBuilder stringBuilder = new StringBuilder();
    //线程安全  使用了synchronized 关键字 任意时刻只能有一个线程操作该对象 性能损耗
    private static StringBuffer stringBuffer = new StringBuffer();

    private static Logger logger = LoggerFactory.getLogger(ConcurrencyTest.class);

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    update();
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        logger.info("stringBuilder:{}", stringBuilder.toString().length());
        logger.info("stringBuffer:{}", stringBuffer.toString().length());

    }

    private static void update() {
        stringBuilder.append("1");
        stringBuffer.append("1");
    }
}
