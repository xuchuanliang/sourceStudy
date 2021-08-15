package com.aop.service.impl;

import com.aop.service.MyLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MyLogicImpl implements MyLogic {
    @Autowired
    private Map<String, com.aop.service.Service> map;
    @Override
    public void query() {
        System.err.println(map);
    }
}
