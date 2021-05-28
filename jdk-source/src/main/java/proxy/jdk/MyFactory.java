package proxy.jdk;

import java.lang.reflect.Proxy;

public class MyFactory {

    public static void main(String[] args) {
        create().sayHello();
    }

    public static MyInterface create(){
        MyInterface instance = (MyInterface) Proxy.newProxyInstance(MyFactory.class.getClassLoader(), new Class[]{MyInterface.class}, new MyProxy());
        return instance;
    }
}
