package com.ant.com.capter02;

import java.util.ArrayList;

public class Test1 {

    private transient String id;

    public static void main(String[] args) throws Exception{
//        System.out.println(exeFinally());
//        cloneTest();
    }

    public static String exeFinally(){
        try{
            System.out.println(1/0);
            return "try";
        }catch (Exception e){
            return "exception;";
        }finally {
            return "finally";
        }
    }

    public static void cloneTest() throws CloneNotSupportedException {
        Friend girlFriend = new Friend();
        girlFriend.setGender("女");
        girlFriend.setName("小红");
        Entity entity = new Entity();
        entity.setGender("男");
        entity.setName("小明");
        entity.setFriend(girlFriend);
        Entity clone = entity.clone();
        girlFriend.setName("小芬");
        girlFriend.setGender("人妖");
        System.out.println(clone);
    }

    public static void arrayListTest(){
        ArrayList arrayList = new ArrayList();
    }
}
