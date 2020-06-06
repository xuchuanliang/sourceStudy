package com.ant.test1;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class IndexService {

    private ApplicationContext applicationContext;


    public void test() {
        createIndexDao().test();
    }

    @Lookup()
    public IndexDao createIndexDao(){
        return null;
    }
}
