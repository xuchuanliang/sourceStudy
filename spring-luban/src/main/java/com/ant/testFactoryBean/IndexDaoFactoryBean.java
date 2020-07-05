package com.ant.testFactoryBean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ComponentScan("com.ant.testFactoryBean")
@Configuration
@Component("&")
public class IndexDaoFactoryBean implements FactoryBean<IndexDao> {

    @Override
    public IndexDao getObject() throws Exception {
        return new IndexDao();
    }

    @Override
    public Class<?> getObjectType() {
        return IndexDao.class;
    }
}
