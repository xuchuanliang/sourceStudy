package com.ant.springboottest;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener {
    @EventListener
    public void listener(MyBO myBO){
        System.out.println(myBO);
    }
}
