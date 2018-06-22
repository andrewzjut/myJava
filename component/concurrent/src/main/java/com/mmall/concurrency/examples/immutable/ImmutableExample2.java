package com.mmall.concurrency.examples.immutable;

import com.mmall.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ThreadSafe
public class ImmutableExample2 {
    private static Map<Integer, Integer> map = new HashMap<>();

    static {
        map.put(1, 2);
        map.put(2, 3);
        map.put(3, 4);
        map = Collections.unmodifiableMap(map);
    }

    public static void main(String[] args) {
        //  map.put(3, 5);  不允许 修改
        log.info("{}", map.get(3));
    }

}
