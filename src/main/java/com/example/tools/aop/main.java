package com.example.tools.aop;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author JinXing
 * @date 2018/11/9 13:21
 */
public class main {


    @Autowired
    private static AsyncMethod asyncMethod;

    public static void main(String[] args) {

        System.out.println("`11111111111111");
        asyncMethod.getName();

        System.out.println("22222222222222222");

    }

}
