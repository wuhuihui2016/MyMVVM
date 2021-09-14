package com.whh.thread.threadlocal;

import java.util.HashMap;
import java.util.Map;

/**
 * 自己实现的 ThreadLocal
 * 一个thread可以有多个threadLocal，即生成的副本多个，因此以数组来保存
 * <p>
 * author:wuhuihui 2021.07.05
 */
public class MyThreadLocal<T> {

    /* 存放变量副本的 map 容器，以Thread 为键，变量副本为 value */
    private final Map<Thread, T> threadTMap = new HashMap<>();

    public synchronized T get() {
        return threadTMap.get(Thread.currentThread());
    }

    public synchronized void set(T t) {
        threadTMap.put(Thread.currentThread(), t);
    }
}
