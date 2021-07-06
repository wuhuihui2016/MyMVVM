package com.whh.thread.thread;

import com.whh.thread.SleepToos;

/**
 * 锁的实例不一样，线程可以并行
 *
 * instance3() 、instance4() 两个方法可以并行，锁加在 static 方法上，
 *    锁的是类的 class 对象，加上静态变量，锁的是静态变量，因此锁的是不同对象，可以并行
 *
 *  author:wuhuihui 2021.07.01
 */
public class DiffInstance {

    private static class InstabceSyn implements Runnable {

        private DiffInstance diffInstance;

        public InstabceSyn(DiffInstance diffInstance) {
            this.diffInstance = diffInstance;
        }

        @Override
        public void run() {
            System.out.println("Test Instance1 is running..." + diffInstance);
            diffInstance.instance();
        }
    }

    private static class InstabceSyn2 implements Runnable {

        private DiffInstance diffInstance;

        public InstabceSyn2(DiffInstance diffInstance) {
            this.diffInstance = diffInstance;
        }

        @Override
        public void run() {
            System.out.println("Test Instance2 is running..." + diffInstance);
            diffInstance.instance2();
        }
    }

    private synchronized void instance() {
        SleepToos.sleep(3);
        System.out.println("Test Instance1 is running..." + this.toString());
        SleepToos.sleep(3);
        System.out.println("Test Instance1 is end..." + this.toString());
    }

    private synchronized void instance2() {
        SleepToos.sleep(3);
        System.out.println("Test Instance2 is running..." + this.toString());
        SleepToos.sleep(3);
        System.out.println("Test Instance2 is end..." + this.toString());
    }


    private static synchronized void instance3() {
        System.out.println(Thread.currentThread().getName() + " instance3 going...");
        SleepToos.sleep(1);
        System.out.println(Thread.currentThread().getName() + " instance3 end");
    }

    private synchronized void instance4() {
        System.out.println(Thread.currentThread().getName() + " instance4 going...");
        SleepToos.sleep(1);
        System.out.println(Thread.currentThread().getName() + " instance4 end");
    }

    public static void main(String[] args) {
        DiffInstance instance1 = new DiffInstance();
        Thread thread = new Thread(new InstabceSyn((instance1)));
        DiffInstance instance2 = new DiffInstance();
        Thread thread2 = new Thread(new InstabceSyn2((instance2)));
        thread.start();
        thread2.start();
        SleepToos.sleep(1);
    }
}
