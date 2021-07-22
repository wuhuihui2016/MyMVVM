package com.whh.others.interfaces;

/**
 * 接口类默认是 static 类，接口类本不可有其他代码，但是可以有内部类
 * 即所在的方法和类自动为 public 和 static 的
 */
public interface Callback {
    void callback(String msg);

    class ImplCallback implements Callback {

        @Override
        public void callback(String msg) {
            System.out.println(msg);
        }

        public static void main(String[] args) {
            new ImplCallback().callback("ImplCallback msg...");
        }
    }
}
