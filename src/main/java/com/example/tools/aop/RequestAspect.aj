package com.example.tools.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author JinXing
 * @date 2018/9/5 19:15
 */

@Aspect
@Component
public class RequestAspect {

    @Pointcut("execution(public * com.example.tools.api.*.*(..))")
    public void webRequest(){}

    //环绕通知,环绕增强，相当于MethodInterceptor  
    @Around("webRequest()")
    public Object around(ProceedingJoinPoint pjp) {
        System.out.println("方法环绕start.....");
        try {
            Object o =  pjp.proceed();
            System.out.println("方法环绕proceed，结果是 :" + o);
            return o;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

}
