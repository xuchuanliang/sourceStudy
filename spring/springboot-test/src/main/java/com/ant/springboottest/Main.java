package com.ant.springboottest;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext("com.ant.springboottest");
        annotationConfigApplicationContext.publishEvent(new MyBO("1234"));
    }
}
