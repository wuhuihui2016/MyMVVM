package com.whh.thread.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 线程的启动和运行
 * 新建 -> 就绪（等待 cpu 分配时间片运行） -> 阻塞(仅在 synchronized 时会进入) -> 运行 -> 死亡
 * start() 线程进入就绪状态，在 cpu 的调度下，会在某个时机调用 run()，此时线程进入运行状态
 * sleep() 线程进入等待状态，不会释放该线程所拥有的资源，比如锁，
 * 如果当前线程持有对某个对象的锁，则即使调用 sleep() 方法，其他线程也无法访问这个对象。
 * <p>
 * >>>>小结<<<<
 * run() 方法只是 Thread 的普通方法；执行时实际运行在主线程；
 * 因此 run() 方法不需要线程调用 start() 后才可以调用的，可随时被调用，但要顺序执行，等待 run() 执行完毕才会继续其他代码
 * 当线程调用了 start() 方法后，一旦线程被 CPU 调度，处于运行状态，那么线程才会去调用 run() 方法；
 * start() 方法才是 Thread 的启动方法，执行时在该 Thread 内部，此时线程处于就绪状态，通过调用 run() 方法来运行，Run方法运行结束，此线程终止，CPU再调度其它线程。
 * start() 方法才真正实现了多线程运行
 * <p>
 * author:wuhuihui 2021.06.30
 */
public class StartAndRunThread {

    public static void main(String[] args) {
        runThread(); //run() 方法为普通方法，在主线程运行
        startThread(); //start() 方法才真正实现了多线程运行
        startCallable(); // Callable
    }

    static class ThreadRun extends Thread {

        int i = 5;

        @Override
        public void run() {
            super.run();
            while (i > 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("I am " + Thread.currentThread().getName()
                        + " thread and now the i = " + i--);
            }

        }
    }

    /**
     * run() 方法为普通方法，在主线程运行
     **/
    private static void runThread() {
        System.out.println("StartAndRunThread run....");
        ThreadRun threadRun1 = new ThreadRun();
        threadRun1.setName("threadRun1");
        threadRun1.run();
        ThreadRun threadRun2 = new ThreadRun();
        threadRun2.setName("threadRun2");
        threadRun2.run();
        //执行完第一个 run(), 才会执行第二个 run()
        /* TODO　run() 为何输出是在 main thread？
        解释：run() 方法只是 Thread 的普通方法；执行时实际运行在主线程；
             因此 run() 方法不需要线程调用 start() 后才可以调用的，可随时被调用，但要顺序执行，等待 run() 执行完毕才会继续其他代码
             当线程调用了 start() 方法后，一旦线程被 CPU 调度，处于运行状态，那么线程才会去调用 run() 方法；
             start() 方法才是 Thread 的启动方法，执行时在该 Thread 内部，此时线程处于就绪状态，通过调用 run() 方法来运行， Run方法运行结束， 此线程终止。然后CPU再调度其它线程。
             start() 方法才真正实现了多线程运行*/
    }

    /**
     * start() 方法才真正实现了多线程运行
     **/
    private static void startThread() {
        System.out.println("StartAndRunThread start....");
        ThreadRun threadStart1 = new ThreadRun();
        threadStart1.setName("threadStart1");
        threadStart1.start();
        // threadStart1.start(); start() 只能被调用一次，否则出现异常 IllegalThreadStateException

        ThreadRun threadStart2 = new ThreadRun();
        threadStart2.setName("threadStart2");
        threadStart2.start();
        //threadStart1、threadStart1 同时运行
    }

    /**
     * Callable 运行
     * 通过 Callable 和 Future 创建线程，实际也是封装在 Runnable 里执行，
     * FutureTask 实现自 RunnableFuture，而 RunnableFuture 继承自 Runnable
     * Thread 的构造方法里没有 Callable 的参数
     */
    private static void startCallable() {
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println(Thread.currentThread().getName() + " is runnning");
                return 0;
            }
        });
        Thread callThread = new Thread(task, "callable");
        callThread.start();
        try {
            System.out.println("callable==>" + task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
