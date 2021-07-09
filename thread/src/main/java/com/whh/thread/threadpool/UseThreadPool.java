package com.whh.thread.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池的实现
 * Executors线程工厂类
 * CachedThreadPool();
 * 说明: 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程.
 * 内部实现：new ThreadPoolExecutor(0,Integer.MAX_VALUE,60L,TimeUnit.SECONDS,new SynchronousQueue());
 * FixedThreadPool(int);
 * 说明: 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
 * 内部实现：new ThreadPoolExecutor(nThreads, nThreads,0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue());
 * SingleThreadExecutor();
 * 说明:创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照顺序执行。
 * 内部实现：new ThreadPoolExecutor(1,1,0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue())
 * ScheduledThreadPool(int);
 * 说明:创建一个定长线程池，支持定时及周期性任务执行。
 * 内部实现：new ScheduledThreadPoolExecutor(corePoolSize)
 * <p>
 *
 * 运行线程池： submit() 该方法返回Future对象；execute() 没有返回值。
 *
 * 关闭线程池： Shutdown:尝试关闭，把当前未执行任务的线程中断； ShutdownNow:不论线程是否在运行，都会尝试中断，只是发出中断信号，还是要看是否处理了中断
 *
 * 为什么使用线程池？
 *  1、降低资源消耗  通过重复利用已创建的线程降低线程创建和销毁造成的消耗；
 *  2、提高响应速度  将线程的创建、销毁放在服务器程序执行的空闲时间段处理，在处理客户请求时就不会有这部分的开销；
 *  3、提高线程的可管理性 线程资源稀缺，如果无限创建，会造成资源消耗和降低系统稳定性，使用线程池可以进行统一分配、调优和 监控。
 *
 * Executor 和 Executors 的区别？
 *   Executor 接口对象能执行我们的线程任务。
 *   ExecutorService 接口继承了 Executor 接口并进行了扩展，能获得任务执行的状态并且可以获取任务的返回值。
 *   使用 ThreadPoolExecutor 可以创建自定义线程池
 *
 *
 * author:wuhuihui 2021.07.06
 */
public class UseThreadPool {

    static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
    static ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    static ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

    static MyRunnable runnable = new MyRunnable();

    public static void main(String[] args) {

        System.out.println("Runtime availableProcessors is " + Runtime.getRuntime().availableProcessors()); //获取 CPU 核心数

        Thread[] threads = new Thread[3];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(runnable, "thread-" + i);
        }

        try {
            for (int i = 0; i < threads.length; i++) {
//                cachedThreadPool.submit(threads[i]);     //pool-1-thread-1...0
//                fixedThreadPool.submit(threads[i]);      //pool-2-thread-1...0
//                singleThreadExecutor.submit(threads[i]); //pool-3-thread-1...0
                scheduledThreadPool.submit(threads[i]);    //pool-4-thread-1...0
            }
        } finally {
            cachedThreadPool.shutdown();
            fixedThreadPool.shutdown();
            singleThreadExecutor.shutdown();
            scheduledThreadPool.shutdown();
        }


    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                System.out.println(Thread.currentThread().getName() + "..." + i);
            }
        }
    }

}
