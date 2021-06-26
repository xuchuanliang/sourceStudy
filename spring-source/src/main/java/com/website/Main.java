package com.website;

import org.springframework.context.support.ClassPathXmlApplicationContext;

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
