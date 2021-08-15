package com.test1.ant.test5;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MyTestImportSelector.class)
public @interface EnableMyTest {
     String name() default "徐传良";
    boolean isAuto() default true;
}
