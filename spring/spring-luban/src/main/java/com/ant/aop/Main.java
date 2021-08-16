package com.test1.ant.aop;

import com.test1.ant.test1.TestMain;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Config.class);
        MyServiceInterface bean = annotationConfigApplicationContext.getBean(MyServiceInterface.class);
        MyServiceInterface bea1 = annotationConfigApplicationContext.getBean(MyServiceInterface.class);
//        bean.test("21");
        bean.test();
        bean.test("haha");
//        bea1.test();
//        byte[] myServices = ProxyGenerator.generateProxyClass("MyService", new Class[]{TestMain.class});
//        FileOutputStream fileOutputStream = new FileOutputStream("D:\\MyService.class");
//        fileOutputStream.write(myServices);
//        fileOutputStream.flush();
//        fileOutputStream.close();
    }
}
