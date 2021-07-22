package com.whh.others.interfaces;

/**
 * 实现接口
 * 实现接口类(CallbackImpl)和接口类(Callback)不能放在一个类(CallbackImpl)里，出现循环继承错误
 * author:wuhuihui 2021.07.22
 */
public class CallbackImpl implements Callback {

    @Override
    public void callback(String msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        CallbackImpl impl = new CallbackImpl();
        impl.callback("callback...");

        //天哪噜！内部类也可以在方法里定义
        class Inner {
            private int i;
            private void fun() {
                System.out.println("inner funn...");
            }
        }

        Inner inner = new Inner();
        System.out.println(inner.i); //外部类可随意访问内部类的成员变量和方法(无视 private、protected)
        inner.fun();
    }

}
