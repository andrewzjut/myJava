package com.tairanchina.zt.lambda;

import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Test1 {
    public static void main(String[] args) {
        Runnable runnable = () -> System.out.println("Hello World");
        runnable.run();

        ActionListener actionListener = event -> System.out.println("button clicked");

        BinaryOperator<Long> add = (x, y) -> x + y;
//        BinaryOperator add2 = (x, y) -> x + y; 需要指定泛型

        add.apply(1l, 2l);

        BinaryOperator<Long> addExplicit = (Long x, Long y) -> x + y;

        Runnable multiStatement = () -> {
            System.out.print("Hello");
            System.out.println(" World");
        };


        String name = "zhang";
//        name = "tong"; //Lambda 表达式中引用既成事实上的 final 变量
        Button button = new Button();
        button.addActionListener(event -> System.out.println("hi " + name));


        List<String> names = new ArrayList();
        names.add("zhang");
        names.add("tong");
        names.add("li");
        names.add("zheng");
        names.add("xian");

        //Consumer
        names.stream().forEach(s -> System.out.print(" hello: " + s));

        System.out.println();
        //Function
        List<String> upperNames = names.stream().map(s -> s.toUpperCase()).collect(Collectors.toList());
        System.out.println(upperNames);

        //Predicate
        List<String> filterNames = names.stream().filter(s -> s.length() > 3).collect(Collectors.toList());
        System.out.println(filterNames);

        ThreadLocal<DateFormatter> formatter = ThreadLocal.withInitial(() -> new DateFormatter(new SimpleDateFormat("dd-MMM-yyyy")));



    }
}
