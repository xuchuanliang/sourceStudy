package com.ant.instantiationAwareBeanPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if(beanClass == Entity.class && beanName.equals("entity")){
            System.out.println(beanClass.getName());
            System.out.println(beanName);
            return new User();
        }
        return null;
    }
}
