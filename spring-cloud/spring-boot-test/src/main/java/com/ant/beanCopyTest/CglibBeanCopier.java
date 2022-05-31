package com.ant.beanCopyTest;

import lombok.SneakyThrows;
import org.springframework.cglib.beans.BeanCopier;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CglibBeanCopier {

    private static volatile BeanCopierManager beanCopierManager = new BeanCopierManager();

    private static CglibBeanCopier instance = new CglibBeanCopier();

    private CglibBeanCopier() {
    }

    public static CglibBeanCopier getInstance() {
        return instance;
    }

    public <T> T copy(Object source, T target) {
        BeanCopier beanCopier = beanCopierManager.getBeanCopier(source.getClass(), target.getClass());
        beanCopier.copy(source, target, null);
        return target;
    }

    @SneakyThrows
    public <T> T copy(Object source, Class<T> target) {
        return this.copy(source,target.newInstance());
    }

    private static class BeanCopierManager {
        private Map<String, Reference<BeanCopier>> beanCopierMap;

        private BeanCopierManager() {
            this.beanCopierMap = new ConcurrentHashMap(16);
        }

        private BeanCopier getBeanCopier(Class<?> source, Class<?> target) {
            String cacheKey = this.generateCacheKey(source, target);
            BeanCopier beanCopier = this.getBeanCopier(cacheKey);
            if (beanCopier == null) {
                beanCopier = BeanCopier.create(source, target, false);
                this.saveCache(cacheKey, beanCopier);
            }

            return beanCopier;
        }

        private String generateCacheKey(Class<?> source, Class<?> target) {
            return source.getName() + "-" + target.getName();
        }

        private void saveCache(String cacheKey, BeanCopier beanCopier) {
            Reference<BeanCopier> beanCopierSoftReference = new SoftReference(beanCopier);
            this.beanCopierMap.put(cacheKey, beanCopierSoftReference);
        }

        private BeanCopier getBeanCopier(String cacheKey) {
            Reference<BeanCopier> beanCopierSoftReference = this.beanCopierMap.get(cacheKey);
            return beanCopierSoftReference == null ? null : beanCopierSoftReference.get();
        }
    }

    public static void main(String[] args) {
        C c = new C();
        c.setAa("aa");
        c.setCc("cc");
        A a = new A();
        a.setName("aa");
        a.setAge(12);
        a.setC(c);
        B copy = CglibBeanCopier.getInstance().copy(a, B.class);
        System.out.println(a);
        System.out.println(copy);
    }
}

class A{
    String name;
    int age;
    C c;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public C getC() {
        return c;
    }

    public void setC(C c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "A{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", c=" + c +
                '}';
    }
}

class B{
    String name;
    int age;
    C c;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public C getC() {
        return c;
    }

    public void setC(C c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "B{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", c=" + c +
                '}';
    }
}
class C{
    String cc;
    String aa;

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getAa() {
        return aa;
    }

    public void setAa(String aa) {
        this.aa = aa;
    }

    @Override
    public String toString() {
        return "C{" +
                "cc='" + cc + '\'' +
                ", aa='" + aa + '\'' +
                '}';
    }
}
