package com.ant.test2;

import org.springframework.stereotype.Service;

@Service
public class MyService1Impl implements MyService1{
    public MyService1Impl(){
        System.out.println("myservice1 construct");
    }
}
