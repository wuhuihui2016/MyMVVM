package com.whh.others.serialize;

import java.io.Serializable;

/**
 * 单例防止反序列化
 */
public class Singleton implements Serializable {

    public static Singleton instance = new Singleton();

    //私有的默认构造函数，保证外界无法直接实例化
    private Singleton() { }

    public static Singleton getInstance(){
        return instance;
    }
}
