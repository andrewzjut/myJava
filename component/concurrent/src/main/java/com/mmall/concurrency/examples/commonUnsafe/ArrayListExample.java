package com.mmall.concurrency.examples.commonUnsafe;

import com.mmall.concurrency.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 常见线程不安全的Collections
 */
@Slf4j
@NotThreadSafe
public class ArrayListExample {
    //请求总数
    private static int clientTotal = 5000;
    //并发数
    private static int threadTotal = 200;
    //线程不安全
    private static ArrayList<Integer> arrayList = new ArrayList<>();
    private static Set<Integer> hashSet = new HashSet<>();
    private static HashMap<Integer, Integer> hashMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(200);
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
        long end = System.currentTimeMillis();
        log.info((end-start) + "ms");
        log.info("arrayList:{}", arrayList.size());
        log.info("hashSet:{}", hashSet.size());
        log.info("hashMap:{}", hashMap.size());


    }

    private static void update(int i) {
        arrayList.add(i);
        hashSet.add(i);
        hashMap.put(i, i);
    }
}
