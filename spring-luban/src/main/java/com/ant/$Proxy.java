//package com.ant;
//
//public class $Proxy implements com.ant.myJdkProxy.v2.IndexDao {
//    private com.ant.myJdkProxy.v2.IndexDaoImpl target;
//    private com.ant.myJdkProxy.v2.IndexInvocationHandler invocationHandler;
//
//    public $Proxy(com.ant.myJdkProxy.v2.IndexDaoImpl target, com.ant.myJdkProxy.v2.IndexInvocationHandler invocationHandler) {
//        this.target = target;
//        this.invocationHandler = invocationHandler;
//    }
//
//    @Override
//    public void test() {
//        try {
//            invocationHandler.invoke(target, target.getClass().getDeclaredMethod("test"));
//        } catch (Throwable t) {
//            t.printStackTrace();
//        }
//
//        @Override
//        public void test (java.lang.String var0){
//            try {
//                invocationHandler.invoke(target, target.getClass().getDeclaredMethod("test", java.lang.String.class), var0);
//            } catch (Throwable t) {
//                t.printStackTrace();
//            }
//
//            @Override
//            public java.lang.String testReturn (java.lang.String var0, java.lang.Integer var1){
//                try {
//                    return (java.lang.String) invocationHandler.invoke(target, target.getClass().getDeclaredMethod("testReturn", java.lang.String.class, java.lang.Integer.class), var0, var1);
//                } catch (Throwable t) {
//                    t.printStackTrace();
//                }
//                return null;
//            }