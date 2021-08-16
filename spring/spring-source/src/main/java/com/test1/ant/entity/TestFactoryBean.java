package com.test1.ant.entity;

import org.springframework.beans.factory.FactoryBean;

public class TestFactoryBean  implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        return new Test();
    }

    @Override
    public Class<?> getObjectType() {
        return Test.class;
    }
}
