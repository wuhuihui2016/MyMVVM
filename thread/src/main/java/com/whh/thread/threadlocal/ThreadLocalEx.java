package com.whh.thread.threadlocal;

import com.whh.thread.SleepToos;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal 提供变量副本，实现线程隔离，线程不安全，会导致内存泄露问题！
 * initialValue() -> set() -> get() -> remove() 查看源码 ThreadLocalMap
 * 每个线程独有一份 ThreadLocalMap，ThreadLocalMap 使用 ThreadLocal 的弱引用(WeakReference)作为 Key，弱引用的对象在 GC 时会被回收。
 *
 * 导致内存泄漏的持有链 查看《threadlocal.png》
 * 当把 threadlocal 变量置为 null 以后，没有任何强引用指向 threadlocal 实例，threadlocal 将会被 gc 回收。
 * 导致 ThreadLocalMap 中出现 key 为 null 的 Entry，无法访问这些 key 为 null 的 Entry 的 value，
 * 如果当前线程再迟迟不结束的话，这些 key 为 null 的 Entry 的 value 将一直存在一条强引用链：
 * Thread Ref -> Thread -> ThreaLocalMap -> Entry -> value，而这块 value 永远不会被访问到了，因而出现内存泄露
 * 解决办法：不需要使用 threadlocal 变量时，调用其 remove() 方法，清除数据
 * ThreadLocal 内存泄漏的根源是：由于 ThreadLocalMap 的生命周期跟 Thread 一样长，如果没有手动删除对应 key 就会导致内存泄漏，而不是因为弱引 用
 *
 *
 * JVM 利用设置 ThreadLocalMap 的 Key 为弱引用，来避免内存泄露。
 * JVM 利用调用 remove、get、set 方法的时候，回收弱引用。
 * 当 ThreadLocal 存储很多 Key 为 null 的 Entry 的时候，而不再去调用 remove、 get、set 方法，那么将导致内存泄漏。
 * 使用线程池+ ThreadLocal 时要小心，因为这种情况下，线程是一直在不断的重复运行的，从而也就造成了 value 可能造成累积的情况
 *
 * 小结
 * 每个 Thread 都有一个 ThreadLocalMap 里面存放了这个 Thread 的所有 ThreadLocal
 * 这个 map 会在 ThreadLocal 设置值的时候去懒加载，再将 ThreadLocal 作为 key 要存的值作为 value 一起放入 map
 * 通过 ThreadLocal 的 hash 值去确定需要 value 放在 Map 的哪个位置
 *
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
