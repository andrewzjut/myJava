package com.tairanchina.zt;

import com.tairanchina.zt.bean.Person;
import com.tairanchina.zt.function.MyIntegerCalculator;
import com.tairanchina.zt.function.ThiConsumer;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.*;

public class ConsumerDemo {
    public static void main(String[] args) {

        ThiConsumer<String, String, String> thiConsumer = (String t, String u, String w) -> {
            System.out.println(String.format("thiConsumer receive-->%s+%s+%s", t, u, w));
        };
        ThiConsumer<String, String, String> thiConsumer2 = (String t, String u, String w) -> {
            System.out.println(String.format("thiConsumer2 receive-->%s+%s+%s", t, u, w));
        };

        thiConsumer.andThen(thiConsumer2).accept("zhang", "tong", "play");

        MyIntegerCalculator myIntegerCalculator = (Integer s1) -> s1 * 2;
        //接口方法具体实现   //s1的参数类型Integer可以不写  隐式 可以由javac通过上下文推导

        System.out.println(myIntegerCalculator.calcIt(5));
//
//
//        List<Person> javaProgrammers = new ArrayList<Person>() {
//            {
//                add(new Person("Elsdon", "Jaycob", "Java programmer", "male", 43, 2000));
//                add(new Person("Tamsen", "Brittany", "Java programmer", "female", 23, 1500));
//                add(new Person("Floyd", "Donny", "Java programmer", "male", 33, 1800));
//                add(new Person("Sindy", "Jonie", "Java programmer", "female", 32, 1600));
//                add(new Person("Vere", "Hervey", "Java programmer", "male", 22, 1200));
//                add(new Person("Maude", "Jaimie", "Java programmer", "female", 27, 1900));
//                add(new Person("Shawn", "Randall", "Java programmer", "male", 30, 2300));
//                add(new Person("Jayden", "Corrina", "Java programmer", "female", 35, 1700));
//                add(new Person("Palmer", "Dene", "Java programmer", "male", 33, 2000));
//                add(new Person("Addison", "Pam", "Java programmer", "female", 34, 1300));
//            }
//        };
//
//        List<Person> phpProgrammers = new ArrayList<Person>() {
//            {
//                add(new Person("Jarrod", "Pace", "PHP programmer", "male", 34, 1550));
//                add(new Person("Clarette", "Cicely", "PHP programmer", "female", 23, 1200));
//                add(new Person("Victor", "Channing", "PHP programmer", "male", 32, 1600));
//                add(new Person("Tori", "Sheryl", "PHP programmer", "female", 21, 1000));
//                add(new Person("Osborne", "Shad", "PHP programmer", "male", 32, 1100));
//                add(new Person("Rosalind", "Layla", "PHP programmer", "female", 25, 1300));
//                add(new Person("Fraser", "Hewie", "PHP programmer", "male", 36, 1100));
//                add(new Person("Quinn", "Tamara", "PHP programmer", "female", 21, 1000));
//                add(new Person("Alvin", "Lance", "PHP programmer", "male", 38, 1600));
//                add(new Person("Evonne", "Shari", "PHP programmer", "female", 40, 1800));
//            }
//        };
//
//        System.out.println("Print programmers names:");
//        javaProgrammers.forEach((p) -> System.out.printf("%s %s; ", p.getFirstName(), p.getSalary()));
//        System.out.println();
//        phpProgrammers.forEach((p) -> System.out.printf("%s %s; ", p.getFirstName(), p.getSalary()));
//
//        // Increase salary by 5% to programmers
//        System.out.println("\n\nIncrease salary by 5% to programmers:");
//        Consumer<Person> giveRaise = e -> e.setSalary(e.getSalary() / 100 * 5 + e.getSalary());
//
//        javaProgrammers.forEach(giveRaise);
//        phpProgrammers.forEach(giveRaise);
//        javaProgrammers.forEach((p) -> System.out.printf("%s earns now $%,d.%n", p.getFirstName(), p.getSalary()));
//        System.out.println();
//        phpProgrammers.forEach((p) -> System.out.printf("%s earns now $%,d.%n", p.getFirstName(), p.getSalary()));
//
//
//        // filter examples
//        // Print PHP programmers that earn more than $1,400
//        System.out.println("\nPHP programmers that earn more than $1,400:");
//        phpProgrammers.stream().filter(person -> person.getSalary() > 1400)
//                .forEach(p -> System.out.printf("%s %s; ", p.getFirstName(), p.getLastName()));
//
//        // Define some filters  自定义过滤器
//        Predicate<Person> ageFilter = p -> p.getAge() > 25;
//        Predicate<Person> salaryFilter = p -> p.getSalary() > 1400;
//        Predicate<Person> genderFilter = p -> "female".equals(p.getGender());
//
//
//        System.out.println("\n\nFemale PHP programmers that earn more than $1,400 and are older than 24 years:");
//
//        phpProgrammers.stream().filter(salaryFilter)
//                .filter(ageFilter)
//                .filter(genderFilter)
//                .forEach(p -> System.out.printf("%s %s; ", p.getFirstName(), p.getLastName()));
//
//        // Reuse filters
//        System.out.println("\n\nFemale Java programmers older than 24 years:");
//        javaProgrammers.stream()
//                .filter(ageFilter)
//                .filter(genderFilter)
//                .forEach((p) -> System.out.printf("%s %s; ", p.getFirstName(), p.getLastName()));
//
//        // limit examples
//        System.out.println("\n\nPrint first 3 Java programmers:");
//        javaProgrammers.stream()
//                .limit(3)
//                .forEach((p) -> System.out.printf("%s %s; ", p.getFirstName(), p.getLastName()));
//
//        System.out.println("\n\nPrint first 3 female Java programmers:");
//        javaProgrammers.stream()
//                .filter(genderFilter)
//                .limit(3)
//                .forEach((p) -> System.out.printf("%s %s; ", p.getFirstName(), p.getLastName()));
//
//        // sorted, collect, limit, min, max examples
//        System.out.println("\n\nSort and print first 5 Java programmers by name:");
//        List<Person> sortedJavaProgrammers = javaProgrammers
//                .stream()
//                .sorted((p, p2) -> (p.getFirstName().compareTo(p2.getFirstName())))
//                .limit(5)
//                .collect(toList());
//
//        sortedJavaProgrammers.forEach((p) -> System.out.printf("%s %s; %n", p.getFirstName(), p.getLastName()));
//
//
//        System.out.println("\nSort and print Java programmers by salary:");
//        sortedJavaProgrammers = javaProgrammers
//                .stream()
//                .sorted((p, p2) -> (p.getSalary() - p2.getSalary()))
//                .collect(toList());
//
//        sortedJavaProgrammers.forEach((p) -> System.out.printf("%s %s %2d; %n", p.getFirstName(), p.getLastName(), p.getSalary()));
//
//        // min is faster than sorting and choosing the first
//        System.out.println("\nGet the lowest Java programmer salary:");
//        Person pers = javaProgrammers
//                .stream()
//                .min((p1, p2) -> (p1.getSalary() - p2.getSalary()))
//                .get();
//
//        System.out.printf("Name: %s %s; Salary: $%,d.", pers.getFirstName(), pers.getLastName(), pers.getSalary());
//
//        System.out.println("\nGet the highest Java programmer salary:");
//        Person person = javaProgrammers
//                .stream()
//                .max((p, p2) -> (p.getSalary() - p2.getSalary()))
//                .get();
//
//        System.out.printf("Name: %s %s; Salary: $%,d.", person.getFirstName(), person.getLastName(), person.getSalary());
//
//        // map, collect examples
//        System.out.println("\nGet PHP programmers first name to String:");
//        String phpDevelopers = phpProgrammers
//                .stream()
//                .map(Person::getFirstName)
//                .collect(joining(" ; "));    // this can be use as a token in further operations
//
//        System.out.println(phpDevelopers);
//
//        System.out.println("\nGet Java programmers first name to Set:");
//        Set<String> javaDevFirstName = javaProgrammers
//                .stream()
//                .map(Person::getFirstName)
//                .collect(toSet());
//        javaDevFirstName.stream().forEach((s) -> System.out.printf("%s ", s));
//        ;
//
//        System.out.println("\nGet Java programmers last name to TreeSet:");
//        TreeSet<String> javaDevLastName = javaProgrammers
//                .stream()
//                .map(Person::getFirstName)
//                .collect(toCollection(TreeSet::new));
//
//        javaDevLastName.stream().forEach((s) -> System.out.printf("%s ", s));
//
//
//        int numProcessorsOrCores = Runtime.getRuntime().availableProcessors();
//        System.out.printf("\n\nParallel version on %s-core machine:", numProcessorsOrCores);
//
//        System.out.println("\nCalculate total money spent for paying Java programmers:");
//        int totalSalary = javaProgrammers.parallelStream().mapToInt(p -> p.getSalary()).sum();
//        System.out.printf("Money spent for paying Java programmers: $%,d %n", totalSalary);
//
//        //Get count, min, max, sum, and average for numbers
//        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
//        IntSummaryStatistics stats = numbers
//                .stream()
//                .mapToInt((x) -> x)
//                .summaryStatistics();
//
//
//        System.out.println("Highest number in List : " + stats.getMax());
//        System.out.println("Lowest number in List : " + stats.getMin());
//        System.out.println("Sum of all numbers : " + stats.getSum());
//        System.out.println("Average of all numbers : " + stats.getAverage());
//
//        Function<String, String> function1 = (x) -> "test result1: " + x;
//
//        //标准的,有花括号, return, 分号.
//        Function<String, String> function2 = (x) -> {
//            return "after function1 "+" test result2: " + x;
//        };
//
//        Function<String, String> function3 = (x) -> {
//            return "before function2 "+" test result3: " + x;
//        };
//
//        System.out.println(function1.apply("98"));
//        System.out.println(function1.andThen(function2).apply("100"));//先执行function1 然后将其结果作为参数传递到function2中
//        System.out.println(function2.andThen(function1).apply("100"));
//        System.out.println(function2.compose(function3).apply("fun100"));//先执行function3 在执行function2
//        System.out.println(Function.identity());
    }

}
