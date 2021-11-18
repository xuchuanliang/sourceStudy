package com.ant.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StatusChangeListener implements ApplicationListener<StatusChangeEvent> {
    @Override
    public void onApplicationEvent(StatusChangeEvent event) {
        final Object source = event.getSource();
        System.out.println("listener get :"+source);
    }
}
