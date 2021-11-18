package com.ant.event;

import org.springframework.context.ApplicationEvent;

public class StatusChangeEvent extends ApplicationEvent {
    public StatusChangeEvent(Taskorder source) {
        super(source);
        System.out.println("source is:" + source);
    }
}
