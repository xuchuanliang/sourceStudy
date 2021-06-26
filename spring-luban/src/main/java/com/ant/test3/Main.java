package com.test1.ant.test3;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("test3.xml");
        Entity bean = classPathXmlApplicationContext.getBean(Entity.class);
    }
}
