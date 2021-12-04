package com.ant.springboottest.annoValidate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@ComponentScan
@Configuration
public class ConfigClass {
    @Bean
    LocalValidatorFactoryBean validator(){
        return new LocalValidatorFactoryBean();
    }
}
