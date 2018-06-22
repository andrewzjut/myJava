package com.mmall.concurrency.examples.singleton;

import com.mmall.concurrency.annotations.NotRecommend;
import com.mmall.concurrency.annotations.ThreadSafe;

/**
 * 懒汉模式
 * 单例实例在第一次使用时进行创建
 */
@ThreadSafe
@NotRecommend
public class SingletonExample3 {
    //私有构造函数
    private SingletonExample3() {

    }

    //单例对象
    private static SingletonExample3 instance = null;

    //静态工厂方法  线程安全  性能开销大
    public static synchronized SingletonExample3 getInstance() {
        //多线程情况下 可能执行多次
        if (instance == null) {
            instance = new SingletonExample3();
        }
        return instance;
    }
}
