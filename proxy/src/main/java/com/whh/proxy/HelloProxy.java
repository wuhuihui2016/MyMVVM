package com.whh.proxy;

/**
 * 静态代理类
 */
public class HelloProxy implements HelloInterface {

    private final HelloInterface helloInterface = new Hello();

    @Override
    public void sayHello() {
        System.out.println("before sayHello");
        helloInterface.sayHello();
        System.out.println("after sayHello");
    }

    public static void main(String[] args) {
        HelloProxy helloProxy = new HelloProxy();
        helloProxy.sayHello();
    }
}
