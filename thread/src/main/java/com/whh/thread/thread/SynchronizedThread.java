package com.whh.thread.thread;

import com.whh.thread.SleepToos;

import java.util.concurrent.TimeUnit;

/**
 * synchronized 可以确保线程互斥的访问同步代码
 *
 * 实现原理
 *  使用 monitorenter 和m onitorexit 指令实现的
 *  monitorenter 指令在编译后插入到同步代码块的开始位置，而 imonitorexit 是插入到方法结束处和异常处
 *  每个monitorenter 必须有对应的 monitorexit 与之配对
 *  任何对象都有一个monitor与之关联
 *
 * 三种应用方法：
 * 普通同步方法（实例方法），锁是当前实例对象 ，进入同步代码前要获得当前实例的锁
 * 静态同步方法，锁是当前类的class对象 ，进入同步代码前要获得当前类对象的锁
 * 同步方法块，锁是括号里面的对象，对给定对象加锁，进入同步代码库前要获得给定对象的锁。
 *
 * 对象锁和类锁
 *   对象锁是用于对象实例方法，或者一个对象实例上的，我们知道，类的对象实例可以有很多个，所以不同对象实例的对象锁是互不干扰的。
 *   类锁是用于类的静态 方法或者一个类的 class 对象上，但 是每个类只有一个 class 对象，每个类只有一个类锁。
 *   类锁和对象锁之间也是互不干扰的
 *
 * synchronized 保证可见性、原子性、有序性
 * <p>
 * 保证该对象不发生变化，才能保证线程安全并行。锁不同的对象，可以并行
 * int i；synchronized(i); 错误代码！锁的是具体的对象，而 int 是基础类型，不是对象。
 * <p>
 * <p>
 * >>>>小结<<<<
 * 1、加锁的目的：不同线程操作同一数据时，在线程占用数据时，其他线程等待，以此拿到最新数据；
 * 2、加锁的方式有三种：普通方法、静态方法、方法块；【见练习一】
 * 3、错误加锁导致数据不能更新，比如加上了不同的实例对象上。【见练习二】
 * 4、同时拥有一把锁，多线程则不可并行
 * 5、锁不同的对象，多线程可以并行
 * 6、加锁时加在具体对象上，继承自Object，不是int、double等基本数据类型
 * 7、线程仅在 synchronizedThread 关键字的时候才会进入阻塞状态
 * <p>
 * author:wuhuihui 2021.06.30
 */
public class SynchronizedThread {

    static boolean flag = true;
    static Object lock = new Object(); // static声明的是同一个对象，可以此为锁
    static SynchronizedThread sync = new SynchronizedThread();

    public static void main(String[] args) {

        //TODO 练习一：
        IncreaseRunnable run = new IncreaseRunnable();
        Thread thread1 = new Thread(run, "increase1");
        thread1.start();
        Thread thread2 = new Thread(run, "increase2");
        thread2.start();
        SleepToos.sleep(500);
        System.out.println(Thread.currentThread().getName()
                + " thread increase count = " + sync.count);
        //练习一解析：thread1、thread2 分别抢锁执行，最后结果count = 10

        //TODO 练习二：
        SyncRunnable sync = new SyncRunnable(0);
//        for (int i = 0; i < 5; i++) {
//            new Thread(sync).start();
//        }
        //练习二解析：锁同一个实例对象 lock，保证有序性
        //锁在不同的对象上，为错误锁，影响了线程安全

        //TODO 练习三：
//        Thread A = new Thread(new Wait(), "wait thread");
//        A.start();
//        TimeUnit.SECONDS.sleep(2);
//        Thread B = new Thread(new Notify(), "notify thread");
//        B.start();
        //练习三解析：以上 threadA、threadB 锁的时同一个对象;
        //threadA 运行，当 flag 为 true 时, threadA 进入 wait 状态，
        //并释放锁，threadB 即进入运行状态，threadA 接着运行
    }

    static class Wait implements Runnable {
        @Override
        public void run() {
            synchronized (lock) { //保证该对象不发生变化，才能保证线程安全并行。
                while (flag) {
                    try {
                        System.out.println(Thread.currentThread() + " flag is true");
                        lock.wait();
                    } catch (InterruptedException e) {

                    }
                }
                System.out.println(Thread.currentThread() + " flag is false");
            }
        }
    }

    static class Notify implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(Thread.currentThread() + " flag is false");
                flag = false;
                lock.notifyAll();
                try {
                    TimeUnit.SECONDS.sleep(7);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int count = 0;

    /* 不加锁时，结果不稳定 */
    public void increase() {
        count++;
    }

    /* 用在同步块上 */
    public void increaseSync1() {
        //锁住其他实例对象，比较灵活
        synchronized (lock) {
            count++;
        }
        //锁住当前的实例对象
//        synchronized (this) {
//            count++;
//        }
    }

    /* 用在方法上 */
    public synchronized void increaseSync2() {
        count++;
    }

    static class IncreaseRunnable implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                //测试两个线程同时累加 i
                sync.increase(); //方法未加锁，最终结果不确定
                //以下俩中不同方法加锁，两个线程同时累加 i，达到同步锁效果，最终值为200
//                sync.increaseSync1();
//                sync.increaseSync2();
                SleepToos.sleep(1);
            }
        }
    }

    static class SyncRunnable implements Runnable {

        private Integer i;

        public SyncRunnable(Integer i) {
            this.i = i;
        }

        @Override
        public void run() {
            synchronized (lock) { //TODO 锁在相同的实例对象上，保证有序性
                //TODO　而synchronized (i) 锁在了不同的对象上，i++ => new Integet(i ++)影响了线程安全
                i++;
                System.out.println(Thread.currentThread().getName() + " i = " + i);
                SleepToos.sleep(100);
            }
        }
    }

}
