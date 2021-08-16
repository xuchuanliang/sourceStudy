package com.test1.ant.aop;

import com.test1.ant.P;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
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
