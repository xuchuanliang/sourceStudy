package com.test1.ant.tuling.yuange;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {
    @Pointcut("execution(* *(..))")
    public void point(){}

    @Before("point()")
    public void before(){
        System.out.println("before");
    }

    @After("point()")
    public void after(){
        System.out.println("after");
    }
}
