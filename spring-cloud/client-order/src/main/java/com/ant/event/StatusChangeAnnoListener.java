package com.ant.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.*;
import org.springframework.stereotype.Component;

@Component
public class StatusChangeAnnoListener {
    @EventListener({StatusChangeEvent.class, ContextRefreshedEvent.class,
            ContextStartedEvent.class, ContextStoppedEvent.class,
            ContextClosedEvent.class})
    public void listener(ApplicationEvent
                                 event) {
        System.out.println("annotation listener:" + event);
    }


}
