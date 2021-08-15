package com.test1.ant.lifeCallback;

import com.test1.ant.P;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class LifeServiceImpl implements LifeService {
    public LifeServiceImpl(){
        P.println("this is construct");
    }

    @Override
    public void test(){
        System.out.println("this is test method");
    }

    @PostConstruct
    public void pre(){
        P.println("pre");
    }

    @PreDestroy
    public void after(){
        P.println("after");
    }
}
