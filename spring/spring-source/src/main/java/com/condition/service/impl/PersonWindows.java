package com.condition.service.impl;

import com.condition.service.Person;

public class PersonWindows implements Person {
    @Override
    public void test() {
        System.out.println("i from windows");
    }
}
