package com.mmall.concurrency.examples.count.atomic;

import com.mmall.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 演示 CAS
 */
@ThreadSafe
@Slf4j
public class AtomicExample5 {
    public volatile int count = 100;

    private static AtomicIntegerFieldUpdater<AtomicExample5> updater =
            AtomicIntegerFieldUpdater.newUpdater(AtomicExample5.class, "count");


    public static void main(String[] args) {
        AtomicExample5 countExample5 = new AtomicExample5();

        if (updater.compareAndSet(countExample5, 100, 120)) {
            log.info("update success 1 :{}", countExample5.getCount());
        }
        if (updater.compareAndSet(countExample5, 100, 120)) {
            log.info("update success 2 :{}", countExample5.getCount());
        } else {
            log.info("update failed:{}", countExample5.getCount());

        }
    }

    public int getCount() {
        return count;
    }
}
