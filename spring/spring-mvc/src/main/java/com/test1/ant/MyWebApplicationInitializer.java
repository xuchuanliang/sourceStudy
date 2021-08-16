package com.test1.ant;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class MyWebApplicationInitializer implements WebApplicationInitializer {
    public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {
        System.out.println("=====启动");

        AnnotationConfigWebApplicationContext annotationConfigWebApplicationContext = new AnnotationConfigWebApplicationContext();
        annotationConfigWebApplicationContext.register(MyConfig.class);
        annotationConfigWebApplicationContext.refresh();

        DispatcherServlet servlet = new DispatcherServlet(annotationConfigWebApplicationContext);
        ServletRegistration.Dynamic dynamic = servletContext.addServlet("app", servlet);
        dynamic.setLoadOnStartup(1);
        dynamic.addMapping("/app/*");
    }
}
