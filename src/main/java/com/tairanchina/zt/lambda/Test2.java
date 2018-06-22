package com.tairanchina.zt.lambda;

import com.tairanchina.zt.bean.Album;
import com.tairanchina.zt.bean.Artist;
import com.tairanchina.zt.bean.SampleData;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class Test2 {
    public static void main(String[] args) {
        List<String> names = new ArrayList();
        names.add("zhang");
        names.add("tong");
        names.add("li");
        names.add("zheng");
        names.add("xian");

        long count = names.stream().map(x -> x.toUpperCase()).filter(x -> x.length() > 3).count();
        System.out.println(count);

        List<String> collected = Stream.of("a", "b", "c")
                .collect(toList());
        System.out.println(collected);

        List<Integer> together = Stream.of(asList(1, 2), asList(3, 4))
                .flatMap(numbers -> numbers.stream())
                .collect(toList());
        System.out.println(together);

        String min = names.stream().min(Comparator.comparing(String::length)).get();
        System.out.println(min);
        String max = names.stream().max(Comparator.comparing(String::length)).get();
        System.out.println(max);


        int sum = Stream.of(1, 2, 3).reduce(1, (integer, integer2) -> integer + integer2);
        sum = Stream.of(1, 2, 3).reduce((integer, integer2) -> integer + integer2).get();
        System.out.println(sum);

        Set<String> origins = SampleData.sampleShortAlbum.getMusicians()
                .filter(artist -> artist.getName().startsWith("The"))
                .map(artist -> artist.getNationality())
                .collect(toSet());
        System.out.println(origins);

        Set<String> trackSet = SampleData.albums.flatMap(album -> album.getTracks2().stream())
                .filter(track -> track.getLength() > 60)
                .map(track -> track.getName()).collect(toSet());
        System.out.println(trackSet);


        long sum2 = add(Stream.of(1l, 2l, 3l));
        System.out.println(sum2);


        long totalMembers = 0;
        for (Artist artist : SampleData.getThreeArtists()) {
            Stream<Artist> members = artist.getMembers();
            totalMembers += members.count();
        }
        System.out.println(totalMembers);

        totalMembers = SampleData.getThreeArtists().stream().map(x -> x.getMembers().count()).reduce(0l, (x, y) -> x + y).intValue();
        System.out.println(totalMembers);


        long lowerNum = "ZHang Tong".chars().filter(Character::isLowerCase).count();
        System.out.println(lowerNum);

        System.out.println(mostLowercaseString(names).get());


    }

    public static long add(Stream<Long> stream) {
        return stream.reduce(0l, (x, y) -> x + y);
    }


    public static int countLowercaseLetters(String string) {
        return (int) string.chars()
                .filter(Character::isLowerCase)
                .count();
    }

    public static Optional<String> mostLowercaseString(List<String> strings) {
        return strings.stream()
                .max(Comparator.comparing(Test2::countLowercaseLetters));
    }

    public static <I, O> List<O> map(Stream<I> stream, Function<I, O> mapper) {
        return stream.reduce(new ArrayList<>(), (acc, x) -> {

            List<O> newAcc = new ArrayList<>(acc);
            newAcc.add(mapper.apply(x));
            return newAcc;
        }, (List<O> left, List<O> right) -> {
            List<O> newLeft = new ArrayList<>(left);
            newLeft.addAll(right);
            return newLeft;
        });
    }

    public static <I> List<I> filter(Stream<I> stream, Predicate<I> predicate) {
        List<I> initial = new ArrayList<>();
        return stream.reduce(initial,
                (List<I> acc, I x) -> {
                    if (predicate.test(x)) {
                        // We are copying data from acc to new list instance. It is very inefficient,
                        // but contract of Stream.reduce method requires that accumulator function does
                        // not mutate its arguments.
                        // Stream.collect method could be used to implement more efficient mutable reduction,
                        // but this exercise asks to use reduce method explicitly.
                        List<I> newAcc = new ArrayList<>(acc);
                        newAcc.add(x);
                        return newAcc;
                    } else {
                        return acc;
                    }
                },
                Test2::combineLists);
    }

    private static <I> List<I> combineLists(List<I> left, List<I> right) {
        // We are copying left to new list to avoid mutating it.
        List<I> newLeft = new ArrayList<>(left);
        newLeft.addAll(right);
        return newLeft;
    }
}
