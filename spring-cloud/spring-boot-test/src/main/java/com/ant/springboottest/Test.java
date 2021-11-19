package com.ant.springboottest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class Test {
    @PostMapping("/test")
    public Person test(@RequestBody Person person){
        System.out.println("controller:"+person);
        return person;
    }
}
