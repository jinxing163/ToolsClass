package com.example.tools.thread;

/**
 * @author JinXing
 * @date 2018/7/11 13:29
 */
public class DaemonThread {


    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                System.out.println("我是子线程...");
            }
        });
        //设置线程为守护线程
        thread.setDaemon(true);
        thread.start();
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {

            }
            System.out.println("我是主线程");
        }
        System.out.println("主线程执行完毕!");


    }

}
