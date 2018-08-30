package com.mmall.concurrency.examples.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock {
    public static void main(String[] args) {
        Ticket s1 = new Ticket();

        Thread t1 = new Thread(s1, "窗口一");
        Thread t2 = new Thread(s1, "窗口二");
        Thread t3 = new Thread(s1, "窗口三");

        t1.start();
        t2.start();
        t3.start();

    }
}

class Ticket implements Runnable {
    private int tickets = 100;
    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(Thread.currentThread().getName() + "尝试获取锁");
                lock.lock();//这里也可以使用synchronized(obj){if中的代码} 要使用同一把锁
                if (tickets > 0) {
                    try {
                        Thread.sleep(100);// 休息一会
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " 正在卖第" + tickets-- + "张票");
                }else {
                    break;
                }
            } catch (Exception e) {
            } finally {
                System.out.println(Thread.currentThread().getName() + "释放锁");
                lock.unlock();//释放锁
            }
        }
    }
}