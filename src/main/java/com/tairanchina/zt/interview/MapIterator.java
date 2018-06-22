package com.tairanchina.zt.interview;


public class MapIterator {
    public static void main(String[] args) {
        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            new Thread(() -> {
                threadLocal.set(index);

                /// do something

                System.out.println(threadLocal.get());

            }).start();
        }
    }
}
