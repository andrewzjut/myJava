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

    private static AtomicInteger count = new AtomicInteger(0);
    private static Logger logger = LoggerFactory.getLogger(AtomicExample2.class);

    public static void main(String[] args) {

        add();
        logger.info("count:{}", count.get());
    }

    private static void add() {
        count.incrementAndGet();

    }
}
