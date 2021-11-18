package com.ant.clientorder;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class MyFactoryBean implements FactoryBean<MyService> {
    public MyFactoryBean() {
        System.out.println("factoryBean construct");
    }

    @Override
    public MyService getObject() throws Exception {
        System.out.println("create bean");
        return new MyService();
    }

    @Override
    public Class<?> getObjectType() {
        return MyService.class;
    }
}
