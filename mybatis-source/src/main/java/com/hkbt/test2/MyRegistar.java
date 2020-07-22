package com.hkbt.test2;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;

public class MyRegistar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        ScannedGenericBeanDefinition userService = (ScannedGenericBeanDefinition) registry.getBeanDefinition("userService");
        userService.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE);
        registry.registerBeanDefinition("userService",userService);
    }
}
