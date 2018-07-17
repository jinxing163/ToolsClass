package com.example.tools.atomic;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author JinXing
 * @date 2018/7/5 19:18
 */
public class AtomicTest {

    static AtomicBoolean atomicBoolean=new AtomicBoolean(true);

    public static void test1(){

        atomicBoolean=new AtomicBoolean(false);
    }

    public static void test2(){
        atomicBoolean=new AtomicBoolean(true);
    }

    public static void main(String[] args) {
        test1();
        System.out.println(atomicBoolean);
        test2();
        System.out.println(atomicBoolean);
    }



}
