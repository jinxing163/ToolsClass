package com.example.tools.aop;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

/**
 * @author JinXing
 * @date 2018/11/9 13:20
 */
@Service
@EnableAsync
public class AsyncMethod {

    @Async
    public String getName(){

        System.out.println(Thread.currentThread().getName());
        System.out.println("我是金星姓名...............");
        return "";
    }

}
