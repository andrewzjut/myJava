package com.tairanchina.zt.lambda;


import java.util.LongSummaryStatistics;
import java.util.stream.Stream;

public class Test4 {
    public static void main(String[] args) {

        LongSummaryStatistics summaryStatistics = Stream.of("1", "2", "4")
                .mapToLong(x -> Long.valueOf(x)).summaryStatistics();
        System.out.printf("Max: %d, Min: %d, Ave: %f, Sum: %d",
                summaryStatistics.getMax(),
                summaryStatistics.getMin(),
                summaryStatistics.getAverage(),
                summaryStatistics.getSum());
        System.out.println();
        //boxed  由 long 封装成  Long
        long sum = Stream.of("1", "2", "4")
                .mapToLong(x -> Long.valueOf(x)).boxed().reduce(0l, (acc, x) -> acc + x).longValue();
        System.out.println(sum);
//        Stream.of("1", "2", "4").forEach();




    }

}
