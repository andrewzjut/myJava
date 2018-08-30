package com.tairanchina.zt.testVolatile;

public class TestVolatile {

    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();
        while (true) {
            //两个线程缓存了各自的flag  flag在main线程永远为false
            if (td.isFlag()) {
                System.out.println("-----------------------------");
                break;
            }
        }
    }
}

class ThreadDemo implements Runnable {
    private boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag=" + flag);
    }

    public boolean isFlag() {
        return flag;
    }
}