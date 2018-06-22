package com.mmall.concurrency.aqs;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class BlockingQueueExample {
    private static final ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue(10);

//    private static final DelayQueue<> delayQueue = new DelayQueue<>(10);

    private static final LinkedBlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue(10);

    private static final PriorityBlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue(10);

    private static final SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();

}
