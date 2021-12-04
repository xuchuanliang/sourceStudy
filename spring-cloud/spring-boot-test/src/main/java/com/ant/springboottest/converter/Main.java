package com.ant.springboottest.converter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.DefaultConversionService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        test();
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ConfigClass.class);
        ConversionService bean = annotationConfigApplicationContext.getBean(ConversionService.class);
    }

    public static void test(){
        DefaultConversionService defaultConversionService = new DefaultConversionService();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        List<String> convert = (List<String>) defaultConversionService.convert(list, TypeDescriptor.forObject(list), TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(String.class)));
        System.out.println(convert);
    }
}
