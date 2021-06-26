package com.test1.ant.lifeCallback;

import com.test1.ant.test1.IndexService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMain {
    public static void main(String[] args) {
//        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring.xml");
//        LifeService bean = classPathXmlApplicationContext.getBean(LifeService.class);
//        bean.test();
//        classPathXmlApplicationContext.refresh();
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Config.class);
        annotationConfigApplicationContext.getBean(IndexService.class).test();
    }
}
