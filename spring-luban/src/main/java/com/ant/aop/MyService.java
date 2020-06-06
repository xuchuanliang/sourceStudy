package com.ant.aop;

import com.ant.P;
import org.springframework.stereotype.Service;

@Service
public class MyService implements MyServiceInterface {
    @Override
    public void test(String t){
        P.println("test logic");
    }

    @Override
    public void test(){
        P.println("test no args");
    }
}
