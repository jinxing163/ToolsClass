package com.example.tools.thread;

/**
 * @author JinXing
 * @date 2018/7/9 17:12
 */
public class RunnableTest implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i< 10; i++) {
            System.out.println("i:" + i);
        }
    }

    public static void main(String[] args) {
        System.out.println("-----多线程创建开始-----");
        // 1.创建一个线程
        RunnableTest createThread = new RunnableTest();
        // 2.开始执行线程 注意 开启线程不是调用run方法，而是start方法
        System.out.println("-----多线程创建启动-----");
        Thread thread = new Thread(createThread);
        thread.start();
        System.out.println("-----多线程创建结束-----");

    }

}
