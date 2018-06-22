package com.mmall.concurrency.examples.immutable;

import com.mmall.concurrency.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@NotThreadSafe
public class ImmutableExample1 {
    private final static Integer a = 1;
    private final static String b = "2";
    private final static Map<Integer, Integer> map = new HashMap<>();

    static {
        map.put(1, 2);
        map.put(2, 3);
        map.put(3, 4);
    }

    public static void main(String[] args) {
//        map = new HashMap<>();
//        a = 2 ;
//        b = "3";

        map.put(3, 5);
        log.info("{}", map.get(3));
    }

    private void test(final int a) {
//        a = 1;
    }
}
