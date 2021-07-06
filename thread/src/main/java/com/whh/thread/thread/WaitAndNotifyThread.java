package com.whh.thread.thread;

import com.whh.thread.Express;
import com.whh.thread.SleepToos;

/**
 * 探索线程的 wait() 和 notify()
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
 *
 * notifyAll() 唤醒所有线程，notify() 仅唤醒某个线程，不确定是哪个，可能唤醒的是无用线程，尽量使用 notifyAll()。
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
