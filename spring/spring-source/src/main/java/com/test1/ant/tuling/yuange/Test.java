package com.test1.ant.tuling.yuange;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MyConfig.class);
        BaseDaoImpl bean = annotationConfigApplicationContext.getBean(BaseDaoImpl.class);
        bean.test();
    }
}
