package com.ant.springboottest.converter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;

@Configuration
@ComponentScan
public class ConfigClass {
    @Bean
    public ConversionServiceFactoryBean conversionService(){
        ConversionServiceFactoryBean serviceFactoryBean = new ConversionServiceFactoryBean();
        return serviceFactoryBean;
    }
}
