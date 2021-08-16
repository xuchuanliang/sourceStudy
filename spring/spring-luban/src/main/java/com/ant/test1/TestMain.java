package com.test1.ant.test1;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestMain {
    public static void main(String[] args) {
//        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring.xml");
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Config.class);
//        IndexService indexService = classPathXmlApplicationContext.getBean(IndexService.class);
//        annotationConfigApplicationContext.getBean(IndexDaoImpl.class);
//        annotationConfigApplicationContext.getBean(IndexDaoImpl.class);
        IndexService indexService = annotationConfigApplicationContext.getBean(IndexService.class);
        indexService.test();
        indexService.test();
    }
}
