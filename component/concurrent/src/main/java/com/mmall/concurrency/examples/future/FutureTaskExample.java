package com.mmall.concurrency.examples.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.FutureTask;

@Slf4j
public class FutureTaskExample {
    public static void main(String[] args) throws Exception {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            log.info("do something");
            Thread.sleep(5000);
            return "Done";
        });

        new Thread(futureTask).start();
        log.info("result:{}",futureTask.get());
    }
}
