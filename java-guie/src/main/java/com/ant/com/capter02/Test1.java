package com.test1.ant.com.capter02;

import java.lang.reflect.Array;
import java.util.*;
import java.util.zip.ZipEntry;

public class Test1 {

    private transient String id;

    public static void main(String[] args) throws Exception{
//        System.out.println(exeFinally());
//        cloneTest();

//        ArrayList arrayList = new ArrayList();
//        arrayList.add("1");
//        arrayList.add("2");
//        arrayList.add("3");
//        arrayList.add("4");
//        arrayList.add("5");
//        arrayList.add("6");
//        arrayList.add("7");
//        arrayList.add("8");
//        arrayList.add("9");
//        arrayList.add("10");
//        arrayList.add("11");
//        ensureCapacityTest();

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

    /**
     * 测试ArrayList的ensureCapacity()方法
     * 该方法是在我们需要向ArrayList中add大量元素时，为了避免出现List内存对数组不断的进行扩容而导致内存和CPU资源的浪费
     * 使用该方法可以一次性在内存中开辟这么大空间的数组，节省大量的时间和系统资源
     * 下方增加1千万个元素，时间差距一倍：
     * 需要不断开辟空间，所使用的时间：2725
     * 一次性预先开辟好，所使用的时间：1135
     */
    public static void ensureCapacityTest(){
        List<Object> list = new ArrayList<>();
        //增加一千万条数据，在没有提前调用ensureCapacity方法之前，ArrayList要频繁的开辟空间，进行数组的拷贝工作，耗费比较多的CPU和内存资源，耗费时间
        int size = 10000000;
        long start = System.currentTimeMillis();
        for(int i=0;i<size;i++){
            list.add(i);
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
        list.clear();
        start = System.currentTimeMillis();
        ((ArrayList)list).ensureCapacity(size);
        for(int i=0;i< size;i++){
            list.add(i);
        }
        end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    public static void hashMapAndHashtable(){
    }
}
