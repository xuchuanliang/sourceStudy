package com.ant.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Random;

@Component
public class MyService {
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;
    public void publish(){
        Taskorder taskorder = new Taskorder(new Random().nextLong(),"name:"+new Random().nextLong(),3);
        applicationEventPublisher.publishEvent(new StatusChangeEvent(taskorder));
    }
}
