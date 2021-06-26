package com.test1.ant.springsecurity.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/test")
    public String test(HttpServletRequest request){
        Enumeration<String> attributeNames = request.getSession().getAttributeNames();
        while (attributeNames.hasMoreElements()){
            System.out.println(request.getSession().getAttribute(attributeNames.nextElement()));
        }
        return "hello security";
    }
}
