package com.ant.springboottest;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonInterceptor implements HandlerInterceptor {
    public CommonInterceptor() {
        System.out.println("CommonInterceptor construnct");
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestWrapper myRequestWrapper = new RequestWrapper((HttpServletRequest) request);
        String body = myRequestWrapper.getBody();
        System.out.println("interceptor:"+body);
        // do something
        return true;

    }
}
