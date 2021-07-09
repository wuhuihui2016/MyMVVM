package com.whh.thread.thread;

import com.whh.thread.Express;
import com.whh.thread.SleepToos;

/**
 * 探索线程的 wait() 和 notify()
 *
 * 在调用 wait（）、notify()系列方法之前，线程必须要获得该对象的对象级 别锁，即只能在同步方法或同步块中调用 wait（）方法、notify()系列方法
 * wait()方法会释放锁，而 notify、notifyAll 不会释放锁
 * <p>
 * 等待和通知的标准范式
 * 等待：
 * synchronized(对象) {
 * while(条件不满足){
 * 对象.wait();
 * }
 * //业务逻辑
 * }
 * 通知：
 * synchronized(对象) {
 * //业务逻辑
 * 对象.notify();/notifyAll();
 * //notify、notifyAll放在 synchronized 方法块的最后
 * }
 *
 * notify() 通知一个在对象上等待的线程,使其从 wait 方法返回,而返回的前提是该线程获取到了对象的锁，没有获得锁的线程重新进入 WAITING 状态
 * notifyAll() 所有等待在该对象上的线程，建议 notifyAll()，防止 notify() 因为信号丢失而造成程序异常。
 * wait() 调用该方法的线程进入 WAITING 状态,只有等待另外线程的通知或被中断 才会返回.需要注意,调用 wait()方法后,会释放对象的锁
 * 调用 wait()方法后，会释放当前线程持有的锁，而且当前被唤醒后，会重新 去竞争锁，锁竞争到后才会执行 wait 方法后面的代码。
 * 调用 notify()系列方法后，对锁无影响，线程只有在 syn 同步代码执行完后才会自然而然的释放锁，所以 notify()系列方法一般都是 syn 同步代码的最后一行
 *
 * wait 和 notify 方法必须在同步块中调用，否则抛出 IllegalMonitorStateException 异常。
 *
 * 由于该业务有地点和距离的两个条件，如果是单个条件，可以使用 notify()，也可以给条件变量加锁
 *
 * author:wuhuihui 2021.07.01
 */
public class WaitAndNotifyThread {

    private static Express express = new Express(0, Express.CITY);

    /**
     * 检查距离变化线程
     * 不满足条件时，线程一直等待
     */
    private static class CheckKm extends Thread {
        @Override
        public void run() {
            express.waitKm();
        }
    }

    /**
     * 检查地点变化线程
     * 不满足条件时，线程一直等待
     */
    private static class CheckSite extends Thread {
        @Override
        public void run() {
            express.waitSite();
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < 3; i++) {
            new CheckSite().start();
        }
        for (int i = 0; i < 3; i++) {
            new CheckKm().start();
        }
        SleepToos.sleep(1000);

        express.changeKm(); //先开启监听线程，再变化通知
    }

}
