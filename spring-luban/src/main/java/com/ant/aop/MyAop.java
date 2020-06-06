package com.ant.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 定义切面
 */
@Component
@Aspect
public class MyAop {

    /**
     * 定义pointCut（切入点）：即连接点的集合
     */
    @Pointcut("execution(* com.ant.aop.*.*(..))")
    public void jointPointExecution(){}

    @Pointcut("within(com.ant.aop.*)")
    public void jointPointWithin(){}

    @Pointcut("args(java.lang.String)")
    public void jointPointArgs(){}

    @Pointcut("this(com.ant.aop.MyService)")
    public void jointPointThis(){}

    @Pointcut("target(com.ant.aop.MyService)")
    public void jointPointTarget(){}



    /**
     * 定义advice（通知）：切面逻辑+切面需要作用到连接点逻辑的位置
     */
    @Around("jointPointExecution()")
    public void advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("before");
        System.out.println(proceedingJoinPoint);
        proceedingJoinPoint.proceed();
        System.out.println("after");
    }


}
