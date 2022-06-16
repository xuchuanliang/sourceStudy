package com.ant.validate;

import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.ArrayList;

@Validated
@RestController
@RequestMapping("/validate")
public class ValidateController{
    @GetMapping("/test1")
    public boolean test1(@Range String name){
        System.out.println(name);
        return true;
    }

    @PostMapping("/test2")
    public boolean test2(@Validated Person person){
        System.out.println(person);
        return true;
    }

    @PostMapping("/test3")
    public boolean test3(@Validated @RequestBody Person person){
        System.out.println(person);
        return true;
    }

    @PostMapping("/test4")
    public boolean test4(@AssertTrue Boolean val){
        System.out.println(val);
        return true;
    }

    @PostMapping("/test5")
    public boolean test5(@DecimalMax("10") Long val){
        System.out.println(val);
        return true;
    }

    @PostMapping("/test6")
    public boolean test6(@Email String val){
        System.out.println(val);
        return true;
    }

    @PostMapping("/test7")
    public boolean test7(@Negative Integer val){
        System.out.println(val);
        return true;
    }

    @PostMapping("/test8")
    public boolean test8(@Size(min = 1,max = 3) ArrayList<String> val){
        System.out.println(val);
        return true;
    }

    @PostMapping("/test9")
    public boolean test9(@Range(min = 10,max = 200)Integer val){
        System.out.println(val);
        return true;
    }

    @PostMapping("/test10")
    public boolean test10(@Validated @RequestBody Page page){
        System.out.println(page.getData());
        return true;
    }
}
