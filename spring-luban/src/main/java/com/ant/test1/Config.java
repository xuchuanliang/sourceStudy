package com.test1.ant.test1;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan(basePackages = {"com.test1.ant"})
//@ImportResource("classpath:spring.xml")
public class Config {
}
