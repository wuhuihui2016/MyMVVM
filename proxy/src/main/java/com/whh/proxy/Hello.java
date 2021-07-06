package com.whh.proxy;

public class Hello implements HelloInterface {

    @Override
    public void sayHello() {
        System.out.println("Hello huihui~");
    }

    /**
     * 析构函数
     * 垃圾回收器（garbage collector）决定回收某对象时，就会运行该对象的finalize()方法
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("hello finalize...");
    }

    public static void main(String[] args) {
        Hello hello = new Hello();
        System.out.println("hello==" + hello);
        hello = null; //并不会调用finalize()方法
    }


}
