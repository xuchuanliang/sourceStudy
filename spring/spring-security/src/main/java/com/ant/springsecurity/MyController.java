package com.ant.springsecurity;

import org.springframework.web.bind.annotation.RequestMapping;

//@org.springframework.stereotype.Controller
//@RequestMapping("/")
public class MyController {
    @RequestMapping("/home")
    public String home(){
        return "home";
    }

    @RequestMapping("/")
    public String root(){
        return "/";
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
