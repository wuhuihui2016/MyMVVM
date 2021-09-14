package com.whh.thread.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 三个线程T1，T2，T3，如何确保它们顺序执行
 * <p>
 * author:wuhuihui 2021.07.06
 */
public class InOrderThread {

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(new MyRunnable("thread1"));
        Thread thread2 = new Thread(new MyRunnable("thread2"));
        Thread thread3 = new Thread(new MyRunnable("thread3"));

        // ××× 方法一：依次start()，并不能顺序！
//        thread1.start();
//        thread2.start();
//        thread3.start();
        // ××× 方法一 end >>>

        // √√√ 方法二：join(); 实现顺序执行：thread1==>thread2==>thread3
//        thread1.start();
//        thread1.join();
//        thread2.start();
//        thread2.join();
//        thread3.start();
//        thread3.join();
        // √√√ 方法二 end >>>

        // √√√ 方法三：在T1的run()中调用T2.start()，在T2的run()中调用T3.start(); 实现顺序执行：thread1==>thread2==>thread3
//        new Thread1().start();
        // √√√ 方法三 end >>>

        // √√√ 方法四：SingleThreadExecutor，其实是在一个线程里，执行了三个任务
        //SingleThreadExecutor 确保了所有任务能够在同一个线程并且按照顺序来执行，不需要考虑线程同步的问题。
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(thread1);
        executor.submit(thread2);
        executor.submit(thread3);
        executor.shutdown();
        // √√√ 方法四 end >>>

    }

    static class MyRunnable implements Runnable {
        private final String name;

        public MyRunnable(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            threadRun(name);
        }
    }

    static class Thread1 extends Thread {
        @Override
        public void run() {
            threadRun("thread1");
            new Thread2().start();
        }
    }

    static class Thread2 extends Thread {
        @Override
        public void run() {
            threadRun("thread2");
            new Thread3().start();
        }
    }

    static class Thread3 extends Thread {
        @Override
        public void run() {
            threadRun("thread3");
        }
    }

    private static void threadRun(String threadName) {
        for (int i = 0; i < 3; i++) {
            System.out.println(threadName + "..." + i);
        }
    }
}
