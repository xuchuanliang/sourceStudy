package com.test1.ant.aop;

import org.springframework.context.annotation.*;

import javax.annotation.Resource;

@Configuration
@ComponentScan(basePackages = {"com.test1.ant.aop"})
@EnableAspectJAutoProxy
@ImportResource
public class Config {
}
