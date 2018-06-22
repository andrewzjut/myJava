package com.mmall.concurrency.examples.sync;

import com.mmall.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@ThreadSafe
public class SynchronizedExamp2 {
    //修饰一个类
    public  void test1(int j) {
        synchronized (SynchronizedExamp2.class) {
            for (int i = 0; i < 10; i++) {
                log.info("test1 - {} - {} ", j, i);
            }
        }
    }

    //修饰静态方法
    public static synchronized void test2(int j) {
        for (int i = 0; i < 10; i++) {
            log.info("test2 - {} - {} ", j, i);
        }
    }


    public static void main(String[] args) {
        SynchronizedExamp2 examp1 = new SynchronizedExamp2();
        SynchronizedExamp2 examp2 = new SynchronizedExamp2();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            examp1.test1(1);
        });
        executorService.execute(() -> {
            examp2.test1(2);
        });
        executorService.shutdown();
    }
}
