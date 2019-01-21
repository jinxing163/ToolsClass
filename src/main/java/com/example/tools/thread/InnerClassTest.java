package com.example.tools.thread;

/**
 * @author JinXing
 * @date 2018/7/9 17:45
 */
public class InnerClassTest {


    public static void main(String[] args) {

        System.out.println("匿名内部类................");

        System.out.println("-----多线程创建开始-----");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i< 10; i++) {
                    System.out.println("i:" + i);
                }
            }
        });
        thread.start();
        System.out.println("-----多线程创建结束-----");

    }

}
