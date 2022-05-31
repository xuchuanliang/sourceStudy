package com.ant.reflections;

import org.reflections.Reflections;
import org.springframework.context.annotation.Conditional;

import java.util.Set;

public class Test {
    public static void main(String[] args) {
        Reflections reflections = new Reflections("org.springframework");
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Conditional.class);
        System.out.println(typesAnnotatedWith);
    }
}
