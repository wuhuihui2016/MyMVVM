package com.whh.thread.lock;

import com.whh.thread.SleepToos;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 解决死锁问题
 * 打破死锁规则：
 * 1、手边有资源时先释放再抢占其他资源，保障其他线程又资源可用；
 * 2、不同一时间去争夺同一资源
 *
 * author:wuhuihui 2021.07.05
 */
public class TryLock {

    private static Lock NO13 = new ReentrantLock();
    private static Lock NO14 = new ReentrantLock();

    /** 先尝试拿 NO13 锁，再尝试拿 NO14 锁，NO14 锁没拿到，连同 NO13 锁释放掉 **/
    private static void first() {
        String threadName = Thread.currentThread().getName();
        Random random = new Random();
        while (true) {
            if (NO13.tryLock()) {
                System.out.println(threadName + " get NO13");
                try {
                    if (NO14.tryLock()) {
                        try {
                            System.out.println(threadName + " get NO14");
                            System.out.println("first do work...");
                        } finally {
                            NO14.unlock();
                        }
                    }
                } finally {
                    NO13.unlock();
                }
            }
            SleepToos.sleep(random.nextInt(3));
        }
    }

    /** 先尝试拿 NO14 锁，再尝试拿 NO13 锁，NO13 锁没拿到，连同 NO14 锁释放掉 **/
    private static void socond() {
        String threadName = Thread.currentThread().getName();
        Random random = new Random();
        while (true) {
            if (NO14.tryLock()) {
                System.out.println(threadName + " get NO13");
                try {
                    if (NO13.tryLock()) {
                        try {
                            System.out.println(threadName + " get NO14");
                            System.out.println("first do work...");
                        } finally {
                            NO13.unlock();
                        }
                    }
                } finally {
                    NO14.unlock();
                }
            }
            SleepToos.sleep(random.nextInt(3));
        }
    }
    

    private static class TestThread extends Thread {
        private String name;

        public TestThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            super.run();
            Thread.currentThread().setName(name);
            socond();
        }
    }

    public static void main(String[] args) {
        //主线程代表 PersonA
        Thread.currentThread().setName("PersonA");
        //新线程代表 PersonB
        TestThread personB = new TestThread("PersonB");
        personB.start();
        first();
        //PersonA 、 PersonB 分别尝试抢占 NO13 、NO14 锁
        //接着 PersonA 、 PersonB 分别去尝试抢占自己未持有的锁，如果拿不到，将自己已有的锁释放
        //此时在双方都释放锁后，又重新获取了想要的锁
    }

}
