package com.ant.test1;

import org.springframework.context.annotation.Scope;
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
