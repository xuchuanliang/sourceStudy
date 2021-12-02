package com.ant.springboottest.propertyEditor;

import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

@ComponentScan
@Configuration
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Main.class);
    }
}
