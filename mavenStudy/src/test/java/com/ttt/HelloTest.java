package com.ttt;

import com.ant.Hello;
import org.junit.Test;

public class HelloTest {

    @Test
    public void testHello(){
        Hello hello = new Hello();
        System.out.println(hello.hello());
    }
}
