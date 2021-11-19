package com.ant.springboottest.scope;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Profile;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.stereotype.Component;

@Profile("test|dev")
@Component
public class Service{
    public void print(){
        System.out.println(prototypeBean());
    }
    @Lookup
    public PrototypeBean prototypeBean(){
        return null;
    }
}
