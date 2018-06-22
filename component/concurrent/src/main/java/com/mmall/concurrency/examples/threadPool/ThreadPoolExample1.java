package com.mmall.concurrency.examples.threadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
public class ThreadPoolExample1 {
    public static void main(String[] args) {

        //四种线程池构造方式
//        ExecutorService executorService = Executors.newCachedThreadPool();

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

//        for (int i = 0; i < 10; i++) {
//            final int index = i;
//            executorService.submit(()->{
//               log.info("task:{}",index);
//            });
//        }


        //延迟3s
//        executorService.schedule(() -> log.info("running task"),3, TimeUnit.SECONDS);

//        executorService.shutdown();

//        executorService.scheduleAtFixedRate(() -> log.info("running task"),1,3,TimeUnit.SECONDS);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("task");
            }
        }, new Date(), 5000);
    }
}
