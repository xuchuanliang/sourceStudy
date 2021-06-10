package com.test1.ant.lifeCallback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = {"com.test1.ant.*"},excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX,pattern = "com.test1.ant.test1.*")})
public class Config {
}
