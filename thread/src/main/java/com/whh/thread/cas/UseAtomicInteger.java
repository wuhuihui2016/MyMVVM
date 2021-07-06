package com.whh.thread.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 基本类型的原子操作类
 * <p>
 * author:wuhuihui 2021.07.05
 */
public class UseAtomicInteger {

    static AtomicInteger atomicInteger = new AtomicInteger(10);

    public static void main(String[] args) {
        atomicInteger.getAndIncrement();
        System.out.println("getAndIncrement...." + atomicInteger.get()); //11

        atomicInteger.incrementAndGet();
        System.out.println("incrementAndGet...." + atomicInteger.get()); //12

        atomicInteger.addAndGet(12);
        System.out.println("addAndGet...." + atomicInteger.get()); //24

        atomicInteger.getAndAdd(12);
        System.out.println("getAndAdd...." + atomicInteger.get()); //36
    }
}
