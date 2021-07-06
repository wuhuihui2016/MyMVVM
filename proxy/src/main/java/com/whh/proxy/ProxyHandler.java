package com.whh.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 * 具体步骤：
 *
 * 通过实现 InvocationHandler 接口创建自己的调用处理器；
 * 通过为 Proxy 类指定 ClassLoader 对象和一组 interface 来创建动态代理类；
 * 通过反射机制获得动态代理类的构造函数，其唯一参数类型是调用处理器接口类型；
 * 通过构造函数创建动态代理类实例，构造时调用处理器对象作为参数被传入。
 *
 */
public class ProxyHandler implements InvocationHandler {

    private Object object;

    public ProxyHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before invoke " + method.getName());
        method.invoke(object, args);
        System.out.println("after invoke " + method.getName());
        return null;
    }

    /**
     * 执行动态代理
     */
    public static void main(String[] args) {
        //是否保存生成的代理类class文件，默认为false，sun.misc包为java的库包，Android不提供
        //生成的class文件位置：/com/sun/proxy
        System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        System.out.println("=======dividingLine========");
        //代理HelloInterface
        HelloInterface helloInterface = new Hello();
        InvocationHandler handler = new ProxyHandler(helloInterface);
        HelloInterface proxyHello = (HelloInterface) Proxy.newProxyInstance(
                helloInterface.getClass().getClassLoader(),
                helloInterface.getClass().getInterfaces(), handler);
        proxyHello.sayHello();

        System.out.println("=======dividingLine========");

        //代理HiInterface
        HiInterface hiInterface = new Hi();
        //实现 InvocationHandler 接口
        InvocationHandler hiHandler = new ProxyHandler(hiInterface);
        //通过为 Proxy 类指定 ClassLoader 对象和一组 interface 来创建动态代理类
        //通过反射机制获得动态代理类的构造函数，其唯一参数类型是调用处理器接口类型
        //通过构造函数创建动态代理类实例，构造时调用处理器对象作为参数被传入
        HiInterface proxyHi = (HiInterface) Proxy.newProxyInstance(
                hiInterface.getClass().getClassLoader(),
                hiInterface.getClass().getInterfaces(), hiHandler);
        proxyHi.sayHi();
        System.out.println("=======dividingLine========");


        /* TODO newProxyInstance方法内部执行流程
        1、利用getProxyClass0(loader, intfs)生成代理类Proxy的Class对象。
        2、ProxyClassFactory内部类创建、定义代理类，返回给定ClassLoader 和interfaces的代理类。
        3、一系列检查后，调用ProxyGenerator.generateProxyClass来生成字节码文件，generateClassFile生成字节码。
        4、字节码生成后，调用defineClass0来解析字节码，生成了Proxy的Class对象。 */
    }
}
