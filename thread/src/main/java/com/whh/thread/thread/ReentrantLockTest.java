package com.whh.thread.thread;

import com.whh.thread.SleepToos;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * author:wuhuihui 2021.07.01
 */
public class ReentrantLockTest {

    private static final Lock lock = new ReentrantLock(true);

    public static void test() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " 拿到了锁");
            SleepToos.sleep(2);
        } finally {
            System.out.println(Thread.currentThread().getName() + " 放开了锁");
        }
    }

    public static void main(String[] args) {
        new Thread("thread1") {
            @Override
            public void run() {
                super.run();
                test();
            }
        }.start();
        new Thread("thread2") {
            @Override
            public void run() {
                super.run();
                test();
            }
        }.start();
    }
}
