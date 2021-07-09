package com.whh.thread.thread;

/**
 * volatile 最轻量的同步机制，保证内存可见性和禁止指令重排
 * 实现原理
 * 有volatile变量修饰的共享变量进行写操作的时候会使用CPU提供的Lock前缀指令
 *  将当前处理器缓存行的数据写回到系统内存
 *  这个写回内存的操作会使在其他CPU里缓存了该内存地址的数据无效。
 *
 * 可见性，不保证线程安全，适用于一写多读，即仅一个线程写，其他线程读
 *
 * 为什么在单个线程写是还要用 volatitle ?  为了在读取时重新从内存中获取
 *
 * volatile 保证有序性，可见性，不能保证原子性
 *   对任意单个 volatile 变量的读/写具有原子性，但类似于 volatile++ 这种复合操作不具有原子性。
 *
 * volatile 用来修饰变量，保证线程可见，为变量的访问提供了一种无锁机制，即该变量随时会被其他线程修改
 * 被修改后不会重排序，不能保证线程安全
 * 不提供任何原子操作，不能修饰 final 类型的变量
 * 程序可以直接从内存中读取该变量，而一般变量的变量值放在寄存器中以加快读写效率
 * author:wuhuihui 2021.07.01
 */
public class VolatileThread implements Runnable {

    public volatile int count = 0;

    @Override
    public void run() {
//        synchronized (this) { //synchronized 可保证数据正确
            count++;
            System.out.println(Thread.currentThread()
                    .getName() + " thread count = " + count);
//        } //synchronized 可保证数据正确
    }

    public static void main(String[] args) {
        VolatileThread run = new VolatileThread();
        Thread threads[] = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(run, "thread-" + i);
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        /**运行结果分析：结果不稳定，不能保证数据有序性和准确性 **/

    }
}
