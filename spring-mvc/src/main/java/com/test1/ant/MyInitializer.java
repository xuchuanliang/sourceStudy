package com.test1.ant;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.util.Set;

@HandlesTypes(MyTest.class)
public class MyInitializer implements ServletContainerInitializer {
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        System.out.println("===============================启动");
        for(Class<?> clazz:c){
        }
    }
}
