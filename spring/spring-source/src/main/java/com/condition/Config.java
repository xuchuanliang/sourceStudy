package com.condition;

import com.condition.service.Person;
import com.condition.service.impl.PersonLinux;
import com.condition.service.impl.PersonWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean("person")
    @Conditional({TestCondition.class,WindowsCondition.class})
    public Person wPerson(){
        return new PersonWindows();
    }

    @Bean("person")
    @Conditional(LinuxCondition.class)
    public Person lPerson(){
        return new PersonLinux();
    }
}
