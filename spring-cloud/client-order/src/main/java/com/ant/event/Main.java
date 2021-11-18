package com.ant.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(Config.class);
        applicationContext.refresh();
        applicationContext.getBean(MyService.class).publish();
        applicationContext.getBean(MyService.class).publish();
        applicationContext.getBean(MyService.class).publish();
        applicationContext.getBean(MyService.class).publish();
    }
}
