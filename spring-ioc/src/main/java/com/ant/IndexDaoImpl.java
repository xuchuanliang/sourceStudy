package com.ant;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
public class IndexDaoImpl implements IndexDao {
    public IndexDaoImpl(){
        System.out.println("init");
    }
    @Override
    public void test() {
        System.out.println("indexDao");
    }
}
