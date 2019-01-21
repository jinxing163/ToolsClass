package com.example.tools.thread;

/**
 * @author JinXing
 * @date 2018/7/9 14:10
 */
public class ThreadTest extends Thread{

    // run方法中编写 多线程需要执行的代码

    @Override
    public void run() {
        for (int i = 0; i< 10; i++) {
            System.out.println("i:" + i);
        }
    }

    public static void main(String[] args) {
        System.out.println("-----多线程创建开始-----");
        // 1.创建一个线程
        ThreadTest createThread = new ThreadTest();
        // 2.开始执行线程 注意 开启线程不是调用run方法，而是start方法
        System.out.println("-----多线程创建启动-----");
        createThread.start();
        System.out.println("-----多线程创建结束-----");
    }


}
