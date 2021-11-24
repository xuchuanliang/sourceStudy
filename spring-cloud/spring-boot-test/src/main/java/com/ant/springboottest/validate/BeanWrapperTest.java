package com.ant.springboottest.validate;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyValue;

public class BeanWrapperTest {
    public static void main(String[] args) {
        BeanWrapper wrapper = new BeanWrapperImpl(new Company());
        wrapper.setPropertyValue("name","some company Inc");
        wrapper.setPropertyValue(new PropertyValue("name","some company Inc2"));
        BeanWrapper emp = new BeanWrapperImpl(new Employee());
        emp.setPropertyValue("name","xuchuanliang");
        emp.setPropertyValue("salary",12.01);
        wrapper.setPropertyValue("managingDirector",emp.getWrappedInstance());
        System.out.println(wrapper.getPropertyValue("managingDirector.salary"));
    }
}
