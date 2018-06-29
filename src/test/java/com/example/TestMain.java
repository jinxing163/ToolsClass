package com.example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JinXing
 * @date 2018/6/25 11:42
 */
public class TestMain {

    static int initialCapacity=10000000;

    public static void test1(){

        long startTime = System.currentTimeMillis();
        List<Integer> list=new ArrayList<>();
        for (int i = 0; i <initialCapacity ; i++) {
            list.add(i);
        }

        long times = System.currentTimeMillis() - startTime;
        System.out.println("test1耗时："+ times+"毫秒,"+ times/1000.0+"秒" );
    }

    public static void test2(){
        long startTime = System.currentTimeMillis();
        List<Integer> list=new ArrayList<>(initialCapacity);
        for (int i = 0; i <initialCapacity ; i++) {
            list.add(i);
        }

        long times = System.currentTimeMillis() - startTime;
        System.out.println("test2耗时："+ times+"毫秒,"+ times/1000.0+"秒" );
    }

    public static void main(String[] args) {
        test1();
        test2();
    }

}
