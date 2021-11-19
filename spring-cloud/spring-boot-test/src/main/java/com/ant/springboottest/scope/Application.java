package com.ant.springboottest.scope;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Locale;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.getEnvironment().setActiveProfiles("dev");
        annotationConfigApplicationContext.register(Application.class);
        annotationConfigApplicationContext.refresh();
        annotationConfigApplicationContext.getBean(Service.class).print();
        annotationConfigApplicationContext.getBean(Service.class).print();
        System.out.println(annotationConfigApplicationContext.getMessage("name", null, "format", Locale.ENGLISH));
    }
}
