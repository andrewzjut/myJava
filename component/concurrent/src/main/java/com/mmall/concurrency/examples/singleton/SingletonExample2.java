package com.mmall.concurrency.examples.singleton;

import com.mmall.concurrency.annotations.ThreadSafe;

/**
 * 饿汉模式
 * 单例实例在类装载时进行创建
 * 前提：构造方法很简单，否则类加载很慢，引起线程问题
 */
@ThreadSafe
public class SingletonExample2 {
    //私有构造函数
    private SingletonExample2() {

    }

    //单例对象
    private static SingletonExample2 instance = new SingletonExample2();

    //静态工厂方法
    public static SingletonExample2 getInstance() {
        return instance;
    }
}
