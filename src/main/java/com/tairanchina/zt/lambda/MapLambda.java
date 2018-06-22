package com.tairanchina.zt.lambda;

import com.alibaba.fastjson.JSONObject;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class MapLambda {
    public static void main(String[] args) throws Exception {
        Map<Integer, String> map = new HashMap();
        for (int i = 0; i < 10; i++) {
            map.put(i, i + "");
        }

        Random random = new Random();
        List<JSONObject> list = new ArrayList();
        for (int i = 1; i < 101; i++) {
            JSONObject jsonObject1 = new JSONObject();

            jsonObject1.put("index", i);
            jsonObject1.put("timestamp", new Date().getTime());
            jsonObject1.put("class", i % 5);
            jsonObject1.put("score", random.nextInt(100));
            Thread.sleep(10);
            list.add(jsonObject1);
        }


        list.sort(Comparator.comparing(x -> -x.getLong("score")));
        list.forEach(x -> System.out.println(x));
        System.out.println();

        Map<String, List<JSONObject>> groupByClass =
                list.stream().collect(
                        Collectors.groupingBy(
                                r -> r.getInteger("class") + "",
                                LinkedHashMap::new,
                                Collectors.toList()));

        //每个班级按成绩分成 优秀  良好  及格   不及格

        Function<JSONObject, String> function = jsonObject -> {
            Integer score = jsonObject.getInteger("score");
            if (score >= 90) {
                return "优秀";
            }
            if (score >= 80) {
                return "良好";
            }
            if (score >= 60) {
                return "及格";
            }
            return "不及格";
        };

        groupByClass.keySet().forEach(c -> {
            Map<String, List<JSONObject>> level =
                    groupByClass.get(c).stream().collect(groupingBy(function, LinkedHashMap::new, Collectors.toList()));
            System.out.println("班级：" + c);

            level.keySet().forEach(l -> {
                System.out.println("等级：" + l);
                level.get(l).forEach(s -> {
                    System.out.println("    " + s);
                });
            });

        });


    }
}


