package com.tairanchina.zt.bishiti;

public class Test1 {
    public static void main(String[] args) {
        String str = new String("hello world");
        char[] chars = {'a', 'b', 'c'};

        change(str, chars);

        System.out.println(str);
        System.out.println(chars);


        /**
         * hello world 传值
         * Abc         传地址
         */

        String a = "hello";
        String b = "world";
        swap(a, b);
        System.out.println(a + " " + b);
    }

    public static void change(String string, char[] chars) {
        string = "hello zhangtong";
        chars[0] = 'A';
    }

    public static void swap(String a, String b) {
        String t = a;
        a = b;
        b = t;
    }
}
