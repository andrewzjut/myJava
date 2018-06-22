package com.mmall.concurrency.examples.threadLocal;


/**
 * 线程封闭  在filter里面add  在 controller service 里面get  remove
 */
public class RequestHolder {
    private final static ThreadLocal<Long> requestHolder =
            new ThreadLocal<>();

    public static void add(Long id) {
        //ThreadLocal 是一个map key = Thread.currentThread().getId() value = id
        requestHolder.set(id);
    }

    public static Long getId() {
        //requestHolder.get(Thread.currentThread().getId())
        return requestHolder.get();
    }

    public static void remove(){
        requestHolder.remove();
    }
}
