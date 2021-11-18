package com.ant.clientorder;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping
@RestController
@ComponentScan
public @interface HkbtController {
    @AliasFor(annotation = RequestMapping.class,attribute = "value")
    String[] value() default {};

}
