package com.mmall.concurrency.examples.count.atomic;

import com.mmall.concurrency.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 演示并发  AtomicInteger
 */
@ThreadSafe
public class AtomicExample2 {

    //请求总数
    private static int clientTotal = 5000;
    //并发数
    private static int threadTotal = 200;
    private static AtomicInteger count = new AtomicInteger(0);
    private static Logger logger = LoggerFactory.getLogger(AtomicExample2.class);

    public static void main(String[] args) throws Exception {
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        final Semaphore semaphore = new Semaphore(threadTotal);
//        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
//        for (int i = 0; i < clientTotal; i++) {
//            executorService.execute(() -> {
//                try {
//                    semaphore.acquire();
//                    add();
//                    semaphore.release();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                countDownLatch.countDown();
//            });
//        }
//        countDownLatch.await();
//        executorService.shutdown();
//        logger.info("count:{}", count.get());

        add();
        logger.info("count:{}", count.get());
    }

    private static void add() {
        count.incrementAndGet();

        /*
         public final int getAndAddInt(Object var1, long var2, int var4) {
        int var5;
        do {
            var5 = this.getIntVolatile(var1, var2);
        } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));

        return var5;
        }

        * */
    }
}
