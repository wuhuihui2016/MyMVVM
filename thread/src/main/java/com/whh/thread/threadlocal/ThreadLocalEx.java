package com.whh.thread.threadlocal;

import com.whh.thread.SleepToos;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal 提供变量副本，实现线程隔离，线程不安全，会导致内存泄露问题！
 * init -> set -> get 查看源码 ThreadLocalMap
 * 导致内存泄漏的持有链 查看《threadlocal.png》
 * author:wuhuihui 2021.06.29
 */
public class ThreadLocalEx {

    //用 ThreadLocal 封装 Integer，并赋初始值
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };

    /**
     * 提供变量副本，实现线程隔离，线程不安全
     */
    public static class TestThread implements Runnable {
        int id;

        public TestThread(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            Integer integer = threadLocal.get();
            integer = integer + id;
            threadLocal.set(integer);
            System.out.println(Thread.currentThread().getName()
                    + ":" + threadLocal.get());
        }
    }

    static class LocalVariable {
        private byte[] a = new byte[1024 * 1024 * 10];
    }

    static ThreadLocal<LocalVariable> localVariable = new ThreadLocal<>();

    public static void main(String[] args) {

        //练习一：初始值为1，
//        Thread thread = new Thread(new TestThread(10));
//        thread.start(); //输出11
        Thread[] runs = new Thread[3];
        for (int i = 0; i < runs.length; i++) {
            runs[i] = new Thread(new TestThread(i + 1), "thread-" + i);
        }
        for (int i = 0; i < runs.length; i++) {
            runs[i].start();
        }
        //练习一解析：通过取出 ThreadLocal 的变量值，线程改变改值，再写回，结果：修改成功！
        //根据多次运行结果，结果值不确定，无序，说明ThreadLocal提供变量副本，实现线程隔离，线程不安全！


        //练习二：ThreadLocal 的内存泄露问题
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    localVariable.set(new LocalVariable());
                    System.out.println("use local localVariable");
                    localVariable.remove(); //TODO 避免内存泄露
                }
            });
            SleepToos.sleep(100);
            System.out.println("pool execute over!");
        }
        //练习二解析：由于每开启一个线程，都会 new LocalVariable，即申请 10M 的数组，
        // 在多线程调度时，将出现内存泄露问题！
        // 处理办法：在单个线程结束时，将其中的 LocalVariable 对象 remove!
    }

}
