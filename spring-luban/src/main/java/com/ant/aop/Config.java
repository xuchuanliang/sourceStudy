package com.ant.aop;

import org.springframework.context.annotation.*;

import javax.annotation.Resource;

@Configuration
@ComponentScan(basePackages = {"com.ant.aop"})
@EnableAspectJAutoProxy
@ImportResource
public class Config {
}
