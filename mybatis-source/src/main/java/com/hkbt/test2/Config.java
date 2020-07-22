package com.hkbt.test2;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.hkbt.test2")
@Import(MyRegistar.class)
public class Config {
}
