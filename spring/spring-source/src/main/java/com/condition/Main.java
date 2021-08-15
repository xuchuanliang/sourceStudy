package com.condition;

import com.condition.service.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Config.class);
        Person p = annotationConfigApplicationContext.getBean(Person.class);
        p.test();
    }
}
