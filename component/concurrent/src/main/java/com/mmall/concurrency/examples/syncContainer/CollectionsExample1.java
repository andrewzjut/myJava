package com.mmall.concurrency.examples.syncContainer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mmall.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 常见线程不安全的Collections
 */
@Slf4j
@ThreadSafe
public class CollectionsExample1 {
    //请求总数
    private static int clientTotal = 5000;
    //并发数
    private static int threadTotal = 200;
    //线程安全
    private static List<Integer> synchronizedList =
            Collections.synchronizedList(Lists.newArrayList());
    private static Set<Integer> synchronizedSet =
            Collections.synchronizedSet(Sets.newHashSet());
    private static Map<Integer,Integer> synchronizedMap =
            Collections.synchronizedMap(Maps.newHashMap());

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
        log.info("synchronizedList:{}", synchronizedList.size());
        log.info("synchronizedSet:{}", synchronizedSet.size());
        log.info("synchronizedMap:{}", synchronizedMap.size());


    }

    private static void update(int i) {
        synchronizedList.add(i);
        synchronizedSet.add(i);
        synchronizedMap.put(i,i);
    }
}
