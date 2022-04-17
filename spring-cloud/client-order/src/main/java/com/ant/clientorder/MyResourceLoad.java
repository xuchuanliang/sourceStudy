package com.ant.clientorder;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationStartupAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.metrics.ApplicationStartup;

import javax.annotation.Resource;

public class MyResourceLoad implements ResourceLoaderAware , ApplicationStartupAware, ApplicationContextAware {

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
    }

    public void load(){
    }

    @Override
    public void setApplicationStartup(ApplicationStartup applicationStartup) {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    }
}
