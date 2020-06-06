package com.website;

import com.ant.entity.Company;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//        Object entity2 = applicationContext.getBean("entity2");
//        System.out.println(entity2);
//        System.out.println(applicationContext.getBean("entity"));
//        System.out.println(applicationContext.getBean("entity3"));
//        GenericApplicationContext genericApplicationContext = new GenericApplicationContext();
//        new XmlBeanDefinitionReader(genericApplicationContext).loadBeanDefinitions("applicationContext.xml");
//        genericApplicationContext.refresh();
//        System.out.println(genericApplicationContext.getBean(Entity.class));
//        Object entity = ac.getBean("entity");
//        System.out.println(entity);
        ac.getBean(ThingsOne.class).test();


    }

}
