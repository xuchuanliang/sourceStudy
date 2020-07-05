package com.ant.test4;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
//        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
//        annotationConfigApplicationContext.register(Config.class);
//        annotationConfigApplicationContext.register(IndexService.class);
//        annotationConfigApplicationContext.refresh();
//        System.out.println(annotationConfigApplicationContext.getBean(IndexService.class));
        System.out.println(Main.class.getResource("/").getPath());

    }
}
