package com.whh.thread.thread;

import com.whh.thread.SleepToos;

/**
 * 守护线程Demo
 * 设置为守护线程，必须在线程启动start()方法之前设置，否则会抛出IllegalThreadStateException异常
 * 即运行中的线程不能设置成守护线程
 * 当守护线程所守护的线程结束时，守护线程自身也会自动关闭
 * Java中垃圾回收线程就是一个典型的Daemon线程。
 *
 * author:wuhuihui 2021.07.06
 */
public class DaemonThreadTest {
    public static void main(String[] args) {
        //创建线程
        Thread daemonThread = new Thread("Daemon") {
            @Override
            public void run() {
                super.run();
                //循环执行5次(休眠一秒，输出一句话)
                for (int i = 0; i < 5; i++) {
                    SleepToos.sleep(1000);
                    System.out.println("守护线程->" + Thread.currentThread().getName() + "正在执行");
                }
            }
        };
        //设置为守护线程，必须在线程启动start()方法之前设置，否则会抛出IllegalThreadStateException异常
        daemonThread.setDaemon(true);
        //启动守护线程
        daemonThread.start();
        //这里是主线程逻辑
        //循环执行3次(休眠一秒，输出一句话)
        for (int i = 0; i < 3; i++) {
            SleepToos.sleep(1000);
            System.out.println("普通线程->" + Thread.currentThread().getName() + "正在执行");
        }
    }
}

