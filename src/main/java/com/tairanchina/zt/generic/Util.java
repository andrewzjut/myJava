package com.tairanchina.zt.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {
    //泛型方法
    public static <K, V> boolean compare(Pair<K, V> p1, Pair<K, V> p2) {
        return p1.getK().equals(p2.getK()) && p1.getV().equals(p2.getV());
    }

//    public static <T> int countGreaterThan(T[] array, T elem) {
//        int count = 0;
//        for (T t : array) {
//            if (t > elem) //除了基本数据类型  其他不能使用 > 所以T 需要实现Comparable 接口
//                ++count;
//        }
//        return count
//    }

    //边界符
    public static <T extends Comparable<T>> int countGreaterThan(T[] array, T elem) {
        int count = 0;
        for (T t : array) {
            if (t.compareTo(elem) > 0)
                ++count;
        }
        return count;
    }


    public static void main(String[] args) {
        Pair<Integer, String> pair1 = new Pair<>(1, "apple");
        Pair<Integer, String> pair2 = new Pair<>(2, "pear");

        boolean same = Util.<Integer, String>compare(pair1, pair2);
        System.out.println(same);
    }
}

class Fruit {
}

class Apple extends Fruit {
}

class Oringe extends Fruit {
}

class GenericReading {
    static List<Apple> apples = Arrays.asList(new Apple());
    static List<Fruit> fruit = Arrays.asList(new Fruit());

    static class Reader<T> {
        T readExact(List<T> list) {
            return list.get(0);
        }
    }

    static void f1() {
        Reader<Fruit> fruitReader = new Reader<Fruit>();
        // Errors: List<Fruit> cannot be applied to List<Apple>.
        // Fruit f = fruitReader.readExact(apples);
    }


    static class CovariantReader<T> {
        T readConvariant(List<? extends T> list) {
            return list.get(0);
        }
    }

    static void f2() {
        CovariantReader<Fruit> fruitCovariantReader = new CovariantReader<>();
        Fruit f = fruitCovariantReader.readConvariant(fruit);
        Fruit a = fruitCovariantReader.readConvariant(apples);
    }

    public static void main(String[] args) {
        f1();
        f2();
    }
}

class GenericWriting {
    static List<Apple> apples = new ArrayList<Apple>();

    static List<Fruit> fruit = new ArrayList<Fruit>();

    static <T> void writeExact(List<T> list, T item) {
        list.add(item);
    }


    static void f1() {
        writeExact(apples, new Apple());
        writeExact(fruit, new Apple());
    }


    static <T> void writeWithWildcard(List<? super T> list, T item) {
        list.add(item);
    }


    static void f2() {
        writeWithWildcard(apples, new Apple());
        writeWithWildcard(fruit, new Apple());
    }


    public static void main(String[] args) {
        f1();
        f2();
    }
}

class ErasedTypeEquivalence {
    public static void main(String[] args) {
        Class c1 = new ArrayList<String>().getClass();
        Class c2 = new ArrayList<Integer>().getClass();
        System.out.println(c1 == c2); // true
    }


//    static <E> void append(List<E> list) {
//        E elem = new E();  // compile-time error
//        list.add(elem);
//    }

    static <E> void append(List<E> list, Class<E> cls) throws Exception {
        E elem = cls.newInstance();   // OK
        list.add(elem);
    }


//    static <E> void rtti(List<E> list) {
//        if (list instanceof ArrayList<Integer>) {  // compile-time error
//            // ...
//        }
//    }

    static void rtti(List<?> list) {
        if (list instanceof ArrayList<?>) {  // OK; instanceof requires a reifiable type
            // ...
        }
    }
}