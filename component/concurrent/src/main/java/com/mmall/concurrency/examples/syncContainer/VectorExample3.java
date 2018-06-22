package com.mmall.concurrency.examples.syncContainer;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Vector;

/**
 * 常见同步容器
 */
@Slf4j

public class VectorExample3 {


    public static void main(String[] args) throws Exception {

        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);

        test3(vector);

    }

    private static void test1(Vector<Integer> v1) { //foreach 循环
        for (Integer i : v1) {
            if (i.equals(3)) {
                v1.remove(i);
                //ConcurrentModificationException
            }
        }
    }

    private static void test2(Vector<Integer> v1) { //iterator 循环
        Iterator<Integer> integerIterator = v1.iterator();
        while (integerIterator.hasNext()) {
            Integer integer = integerIterator.next();
            if (integer.equals(3)) {
                v1.remove(integer);
                //ConcurrentModificationException
            }
        }
    }

    private static void test3(Vector<Integer> v1) { //For 循环
        for (int i = 0; i < v1.size(); i++) {
            log.info(v1 + "");
            v1.remove(i);
            log.info(v1 + "");
            log.info("");
        }
    }
}
