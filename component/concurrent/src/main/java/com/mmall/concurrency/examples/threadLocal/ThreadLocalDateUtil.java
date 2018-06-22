package com.mmall.concurrency.examples.threadLocal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadLocalDateUtil {
    private static final String date_format = "yyyy-MM-dd HH:mm:ss";
    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();

    public static DateFormat getDateFormat() {
        DateFormat df = threadLocal.get();
        if (df == null) {
            df = new SimpleDateFormat(date_format);
            System.out.println("构造");
            threadLocal.set(df);
        }
        return df;
    }

    /*
    确实，ThreadLocal的使用不是为了能让多个线程共同使用某一对象，
    而是我有一个线程A，其中我需要用到某个对象o，这个对象o在这个线程A之内会被多处调用，
    而我不希望将这个对象o当作参数在多个方法之间传递，于是，我将这个对象o放到TheadLocal中，
    这样，在这个线程A之内的任何地方，只要线程A之中的方法不修改这个对象o，我都能取到同样的这个变量o。
    */

    public static String formatDate(Date date) throws ParseException {
        return getDateFormat().format(date);
    }

    public static Date parse(String strDate) throws ParseException {
        return getDateFormat().parse(strDate);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    System.out.println(formatDate(new Date()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
