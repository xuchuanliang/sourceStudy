package com.ant.beanLife;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Config.class);
        Person bean = annotationConfigApplicationContext.getBean(Person.class);
        System.out.println(bean);
    }
}
