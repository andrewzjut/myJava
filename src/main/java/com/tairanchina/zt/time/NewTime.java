package com.tairanchina.zt.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.YEARS;

public class NewTime {
    public static void main(String[] args) {

        //示例2 如何在Java 8中获取当前的年月日
        LocalDate today = LocalDate.now();
        System.out.println(today);

        int year = today.getYear();

        int month = today.getMonthValue();

        int day = today.getDayOfMonth();

        System.out.printf("Year : %d Month : %d day : %d \t %n", year, month, day);

        //示例3 在Java 8中如何获取某个特定的日期
        LocalDate dateOfBirth = LocalDate.of(2010, 01, 14);

        System.out.println("Your Date of birth is : " + dateOfBirth);

        LocalDate date1 = LocalDate.of(2018, 05, 1);
        if (date1.equals(today)) {
            System.out.printf("Today %s and date1 %s are same date %n", today, date1);
        }

        LocalDate dateOfBirth2 = LocalDate.of(2010, 05, 1);
        MonthDay birthday = MonthDay.of(dateOfBirth2.getMonth(), dateOfBirth2.getDayOfMonth());
        MonthDay currentMonthDay = MonthDay.from(today);
        if (currentMonthDay.equals(birthday)) {
            System.out.println("Many Many happy returns of the day !!");
        } else {
            System.out.println("Sorry, today is not your birthday");
        }

        LocalTime time = LocalTime.now();
        System.out.println("local time now : " + time);


//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String formatDate = dateFormat.format(time.getLong());
//        System.out.println(formatDate);

        LocalTime time2 = LocalTime.now();
        LocalTime newTime = time2.plusHours(2); // adding two hours
        System.out.println("Time after 2 hours : " + newTime);


        LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
        System.out.println("Today is : " + today);
        System.out.println("Date after 1 week : " + nextWeek);

        LocalDate previousYear = today.minus(1, YEARS);
        System.out.println("Date before 1 year : " + previousYear);
        LocalDate nextYear = today.plus(1, YEARS);
        System.out.println("Date after 1 year : " + nextYear);


        Clock clock = Clock.systemUTC();
        System.out.println("Clock : " + clock);

        // Returns time based on system clock zone Clock defaultClock =
        Clock.systemDefaultZone();
        System.out.println("Clock : " + clock);

        LocalDate tomorrow = LocalDate.of(2014, 1, 15);
        if (tomorrow.isAfter(today)) {
            System.out.println("Tomorrow comes after today");
        }
        LocalDate yesterday = today.minus(1, DAYS);
        if (yesterday.isBefore(today)) {
            System.out.println("Yesterday is day before today");
        }

//        LocalDateTime localtDateAndTime = LocalDateTime.now();
//        ZonedDateTime dateAndTimeInNewYork = ZonedDateTime.of(localtDateAndTime, america );
//        System.out.println("Current date and time in a particular timezone : " + dateAndTimeInNewYork);

        //如何在Java 8中检查闰年
        if (today.isLeapYear()) {
            System.out.println("This year is Leap year");
        } else {
            System.out.println("2014 is not a Leap year");
        }

        //两个日期之间包含多少天，多少个月
        LocalDate java8Release = LocalDate.of(2014, Month.MARCH, 14);
        Period periodToNextJavaRelease =
                Period.between(today, java8Release);
        System.out.println("Months left between today and Java 8 release : " + periodToNextJavaRelease.getMonths());


        //如何在Java 8中使用预定义的格式器来对日期进行解析/格式化

        String dayAfterTomorrow = "20140116";
        LocalDate formatted = LocalDate.parse(dayAfterTomorrow,
                DateTimeFormatter.BASIC_ISO_DATE);
        System.out.printf("Date generated from String %s is %s %n", dayAfterTomorrow, formatted);


        String goodFriday = "04 18 2014";
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy");
            LocalDate holiday = LocalDate.parse(goodFriday, formatter);
            System.out.printf("Successfully parsed String %s, date is %s%n", goodFriday, holiday);
        } catch (DateTimeParseException ex) {
            System.out.printf("%s is not parsable!%n", goodFriday);
            ex.printStackTrace();
        }

        LocalDateTime arrivalDate = LocalDateTime.now();
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm:ss");
            String landing = arrivalDate.format(format);
            System.out.printf("Arriving at : %s %n", landing);
        } catch (DateTimeException ex) {
            System.out.printf("%s can't be formatted!%n", arrivalDate);
            ex.printStackTrace();
        }


        String update_time = "2018-05-28T04:58:57+08:00";
        System.out.println(LocalDate.ofEpochDay(Integer.valueOf(17679)));
        System.out.println(LocalDateTime.ofEpochSecond(1527483529000000l / 1000000, 0, ZoneOffset.ofHours(0)));
        System.out.println(LocalTime.ofSecondOfDay(17923000000l / 1000000).toString());


        long now = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println(now);

        Long timestamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(timestamp / 1000, 0, ZoneOffset.ofHours(8));
        System.out.println(localDateTime);
        ZoneId zoneId = ZoneOffset.UTC;
        DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        System.out.println(LocalDateTime.now().atZone(zoneId).format(FORMATTER));

        long NANOSECONDS_PER_DAY = TimeUnit.DAYS.toNanos(1);
        long NANOSECONDS_PER_MICROSECOND = TimeUnit.MICROSECONDS.toNanos(1);
        String temp = "2018-06-12T14:20:59";
        LocalDateTime localDateTime1 = LocalDateTime.parse("2018-06-12 14:20:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        long nanoInDay = localDateTime1.toLocalTime().toNanoOfDay();
        long nanosOfDay = localDateTime1.toLocalDate().toEpochDay() * NANOSECONDS_PER_DAY;

        System.out.println(Math.floorDiv(nanoInDay + nanosOfDay, NANOSECONDS_PER_MICROSECOND));
    }
}
