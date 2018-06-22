package com.mmall.concurrency.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
public class SemaphoneExample3 {
    private final static int threadCount = 21;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            executorService.execute(() -> {
                try {
                    if (semaphore.tryAcquire()){//尝试获取一个许可
                        test(threadNum);
                        semaphore.release();//释放许可
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        log.info("结束");
        executorService.shutdown();
    }

    private static void test(int threadNum) throws Exception {
        Thread.sleep(1000);
        log.info("{}", threadNum);
    }
}
