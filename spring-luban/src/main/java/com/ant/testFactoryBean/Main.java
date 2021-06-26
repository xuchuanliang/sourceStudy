package com.test1.ant.testFactoryBean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(IndexDaoFactoryBean.class);
//        IndexDao bean = annotationConfigApplicationContext.getBean(IndexDao.class);
//        IndexDaoFactoryBean bean1 = annotationConfigApplicationContext.getBean(IndexDaoFactoryBean.class);
//        System.out.println(bean);
//        System.out.println(bean1);
        System.out.println(annotationConfigApplicationContext.getBean("&indexDaoFactoryBean"));
    }
}
