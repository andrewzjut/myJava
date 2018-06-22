package com.tairanchina.zt.interview.singleton;

public class Singleton {
    /**
     懒汉 线程不安全， instance 被多次 实例化
     
     private static Singleton instance;
     
     private Singleton(int index) {
     System.out.println("实例化： " + index);
     }
     
     public static Singleton getInstance(int index) {
     if (instance == null) {
     instance = new Singleton(index);
     }
     return instance;
     }
     
     public static void main(String[] args) {
     for (int i = 0; i < 10; i++) {
     final int index = i;
     new Thread(() -> {
     Singleton singleton = getInstance(index);
     }).start();
     }
     }
     
     实例化： 0
     实例化： 3
     实例化： 2
     实例化： 1
     */


    /**
     * 懒汉 线程安全 同步代码 只会实例化一次

     private static Singleton instance;

     private Singleton(int index) {
     System.out.println("实例化： " + index);
     }

     public static synchronized Singleton getInstance(int index) {
     if (instance == null) {
     instance = new Singleton(index);
     }
     return instance;
     }

     public static void main(String[] args) {
     for (int i = 0; i < 10; i++) {
     final int index = i;
     new Thread(() -> {
     Singleton singleton = getInstance(index);
     }).start();
     }
     }
     实例化： 0
     */

    /**
     * 饿汉 实例化一次
     * 
     * private static Singleton instance = new Singleton();
     * 
     * private Singleton() {
     * System.out.println("实例化： ");
     * }
     * 
     * public static Singleton getInstance() {
     * return instance;
     * }
     * 
     * public static void main(String[] args) {
     * for (int i = 0; i < 10; i++) {
     * final int index = i;
     * new Thread(() -> {
     * Singleton singleton = getInstance();
     * }).start();
     * }
     * }
     * 
     * 实例化：
     */

    /**
     * 饿汉 变种
     * 
     * private static Singleton instance = null;
     * 
     * static {
     * instance = new Singleton();
     * }
     * 
     * private Singleton() {
     * }
     * 
     * public static Singleton getInstance() {
     * return instance;
     * }
     */

    /**
     * 静态内部类

     private static class SingletonHolder {
     private static final Singleton INSTANCE = new Singleton();
     }

     private Singleton() {
     }

     public static final Singleton getInstance() {
     return SingletonHolder.INSTANCE;
     }
     */

    /**
     * 枚举
     * 
     * public enum Singleton {
     * INSTANCE;
     * public void whateverMethod() {
     * }
     * }
     */
    /**
     * （双重校验锁）

     private volatile static Singleton singleton;

     private Singleton(int index) {
     System.out.println("实例化： " + index);

     }

     public static Singleton getSingleton(int index) {
     if (singleton == null) {
     synchronized (Singleton.class) {
     if (singleton == null) {
     singleton = new Singleton(index);
     }
     }
     }
     return singleton;
     }

     public static void main(String[] args) {
     for (int i = 0; i < 10; i++) {
     final int index = i;
     new Thread(() -> {
     Singleton singleton = getSingleton(index);
     }).start();
     }
     }
     */
}
