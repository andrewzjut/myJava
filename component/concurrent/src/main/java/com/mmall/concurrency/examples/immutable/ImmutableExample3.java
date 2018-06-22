package com.mmall.concurrency.examples.immutable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mmall.concurrency.annotations.ThreadSafe;


@ThreadSafe
public class ImmutableExample3 {
    private final static ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);

    private final static ImmutableSet<Integer> set = ImmutableSet.copyOf(list);

    private final static ImmutableMap<Integer, Integer> map =
            ImmutableMap.of(1, 2, 3, 4, 5, 6);
    private final static ImmutableMap<Integer, Integer> map2 =
            ImmutableMap.<Integer, Integer>builder().put(1, 2).build();

    public static void main(String[] args) {
//        list.add(4)
//        map.put(1, 2);

    }

}
