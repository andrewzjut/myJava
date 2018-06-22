package com.mmall.concurrency.examples.syncContainer;

import com.mmall.concurrency.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Vector;

/**
 * 常见同步容器
 */
@Slf4j
@NotThreadSafe
public class VectorExample2 {

    private static List<Integer> vector = new Vector<>();


    public static void main(String[] args) throws Exception {

        while (true) {
            for (int i = 0; i < 10; i++) {
                vector.add(i);
            }

            new Thread(() -> {
                for (int i = 0; i < vector.size(); i++) {
                    vector.remove(i);
                    //3: 获取对象锁 vector.remove(9);
                    //4: 释放 对象锁
                }
            }).start();
            new Thread(() -> {
                //1 获取对象锁 vector.size() = 10  2: 释放 对象锁
                for (int i = 0; i < vector.size(); i++) {
                    vector.get(i);
                    //5: 获取对象锁   vector.get(9) 越界 数据已经被删除
                }
            }).start();
        }
    }

    private static void update(int i) {
        vector.add(i);
    }
}
