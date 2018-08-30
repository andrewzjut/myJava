package com.tairanchina.zt.function;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BiConsumerTest {
    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");


        String name = "zhang";

        map.entrySet().forEach(x ->
                biConsumer(name).accept(x.getKey(), x.getValue())
        );

        map.forEach(biConsumer("tong"));


        HashMap<String, Integer> map2 = new HashMap<>();
        map2.put("one", 1);
        map2.put("two", 2);
        map2.put("three", 3);

        map2.forEach(biConsumer("tong"));

    }

    public static <T, V> BiConsumer<T, V> biConsumer(String name) {
        return (a, b) -> System.out.println(a + "=" + b + " ");
    }

    //consumer 方法  入参 name  返回值  是一个consumer ：b -> System.out.println(b);
    public static Consumer<String> consumer(String name) {
        return b -> System.out.println(b);
    }
}


