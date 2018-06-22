package com.mmall.concurrency.examples.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SynchronizedExamp1 {
    //修饰代码块
    public void test1(int j) {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                log.info("test1 - {} - {} ", j, i);
            }
        }
    }

    //修饰方法
    public synchronized void test2(int j) {
        for (int i = 0; i < 10; i++) {
            log.info("test2 - {} - {} ", j, i);
        }
    }


    public static void main(String[] args) {
        SynchronizedExamp1 examp1 = new SynchronizedExamp1();
        SynchronizedExamp1 examp2 = new SynchronizedExamp1();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            examp1.test2(1);
        });
        executorService.execute(() -> {
            examp2.test2(2);
        });
        executorService.shutdown();
    }
}
