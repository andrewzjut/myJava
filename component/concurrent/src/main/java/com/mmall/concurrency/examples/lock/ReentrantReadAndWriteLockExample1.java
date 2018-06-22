package com.mmall.concurrency.examples.lock;

import com.mmall.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 演示并发
 */
@Slf4j
@ThreadSafe
public class ReentrantReadAndWriteLockExample1 {

    private static final Map<String, Data> map = new TreeMap<>();

    public static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private static final Lock readLock = lock.readLock();

    private static final Lock writeLock = lock.writeLock();


    class Data {

    }


    private Data get(String key) {
        readLock.lock();
        try {
            return map.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public Set<String> getAllKeys() {
        readLock.lock();
        try {
            return map.keySet();
        } finally {
            readLock.unlock();
        }
    }

    private Data put(String key, Data data) {
        //有读取锁的时候 不允许任何写操作
        writeLock.lock();
        try {
            return map.put(key, data);
        } finally {
            readLock.unlock();
        }
    }
}
