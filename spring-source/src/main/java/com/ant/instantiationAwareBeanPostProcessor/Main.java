package com.ant.instantiationAwareBeanPostProcessor;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring.xml");
        Object entity = classPathXmlApplicationContext.getBean("entity");
        System.out.println(entity);
    }
}
