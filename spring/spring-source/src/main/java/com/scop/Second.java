package com.scop;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Second {

    @Resource
    private First first;

    public void test() {
        System.out.println("first is " + first);
    }
}
