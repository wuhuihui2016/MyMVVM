package com.whh.thread.lock;

import com.whh.thread.SleepToos;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 死锁的产生
 * 1、多个操作者争夺多个资源；
 * 2、争夺资源的顺序不对；
 * 3、拿到资源不放手
 *
 * author:wuhuihui 2021.07.05
 */
public class NormalDeadLock {

    private static Object NO13 = new Object();
    private static Object NO14 = new Object();

    /**
     * A 持有 NO13 后去拿 NO14
     */
    private static void personADo() {
        String threadName = Thread.currentThread().getName();
        synchronized (NO13) {
            System.out.println(threadName + " get NO13");
            SleepToos.sleep(100);
            synchronized (NO14) {
                System.out.println(threadName + " get NO14");
            }
        }
    }

    /**
     * B 持有 NO14 后去拿 NO13
     */
    private static void personBDo() {
        String threadName = Thread.currentThread().getName();
        synchronized (NO14) {
            System.out.println(threadName + " get NO14");
            SleepToos.sleep(100);
            synchronized (NO13) {
                System.out.println(threadName + " get NO13");
            }
        }
    }

    private static class PersonB extends Thread {
        private String name;

        public PersonB(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            super.run();
            Thread.currentThread().setName(name);
            personBDo();
        }
    }

    public static void main(String[] args) {
        //主线程代表 PersonA
        Thread.currentThread().setName("PersonA");
        //新线程代表 PersonB
        PersonB personB = new PersonB("PersonB");
        personB.start();
        personADo();
        //PersonA 先持有 NO13， PersonB 先持有 NO14
        //等待 100ms 后，PersonA 还想要 NO14， 而不释放 NO13，同样 PersonB 也才想持有 NO13
        //导致出现死锁，最终PersonA 仅持有 NO13， PersonB 仅持有 NO14
    }

}
