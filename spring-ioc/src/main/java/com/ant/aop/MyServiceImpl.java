package com.ant.aop;

import org.springframework.stereotype.Component;

@Component
public class MyServiceImpl implements MyService{
    @Override
    public void eat(String name) {
        System.out.println(name);
    }
}
