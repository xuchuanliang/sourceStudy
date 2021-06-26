package com.test1.ant.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 定义切面
 */
@Component
@Scope("prototype")
@Aspect()
//@Aspect("perthis(this(com.test1.ant.aop.MyServiceInterface))")
public class MyAop {

    /**
     * 定义pointCut（切入点）：即连接点的集合
     */
    @Pointcut("execution(* com.test1.ant.aop.*.*(..))")
    public void jointPointExecution(){}

    @Pointcut("within(com.test1.ant.aop.*)")
    public void jointPointWithin(){}

    @Pointcut("args(java.lang.String)")
    public void jointPointArgs(){}

    @Pointcut("this(com.test1.ant.aop.MyService)")
    public void jointPointThis(){}

    @Pointcut("target(com.test1.ant.aop.MyService)")
    public void jointPointTarget(){}



    /**
     * 定义advice（通知）：切面逻辑+切面需要作用到连接点逻辑的位置
     */
    @Around("jointPointExecution()")
    public void advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("before");
        proceedingJoinPoint.proceed();
        System.out.println("after");
        System.out.println(this.hashCode());
    }


}
