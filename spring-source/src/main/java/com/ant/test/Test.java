package com.ant.test;

import com.ant.entity.Company;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class Test {
    public static void main(String[] args) {
//        xmlBeanFactory();
//        applicationContext();
        applicationContext2();
    }

    public static void xmlBeanFactory(){
        BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));
        Company company = beanFactory.getBean(Company.class);
        System.out.print(company);
    }

    public static void applicationContext(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Company company = applicationContext.getBean(Company.class);
        System.out.print(company);
    }

    public static void applicationContext2(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Object company = applicationContext.getBean("test");
        System.out.print(company);
    }
}
