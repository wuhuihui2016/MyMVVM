package com.whh.thread.thread;

/**
 * yield() 将线程从运行转到可运行状态(但是不能保证正在运行状态迅速转为可运行状态)，让出 cpu 的执行权，不会释放锁
 * 调用 yield()方法并不会让线程进入等待或阻塞状态，而是让线程重回就绪状态，它只需要等待重新得到 CPU 的执行；
 * 与线程优先级有关，当某个线程调用yield()方法时，就会从运行状态转换到就绪状态后，CPU 从就绪状态线程队列中只会选择与该线程优先级相同或者更高优先级的线程去执行。
 * join()  插队等待，如果让两个线程顺序执行，使用该方法
 * 当前线程调用其他线程的 join() 方法，会阻塞当前线程，线程进入阻塞状态，直到其他线程执行完毕，才会进入就绪状态
 * join() 方法是通过 wait()方法 ( Object 提供的方法) 实现的。
 * join() 方法同样会让线程交出CPU执行权限； join() 方法同样会让线程释放对一个对象持有的锁；
 * <p>
 * author:wuhuihui 2021.06.30
 */
public class YieldAndJoinThread {

    static class MyThread extends Thread {

        int i = 3;

        public MyThread(String name) {
            setName(name);
        }

        @Override
        public void run() {
            super.run();
            while (i > 0) {
                if (i == 2) {
                    yield(); //并不会很影响多线程的调度啊！当前线程不一定能立即转成就绪状态
                    //System.out.println("I am " + Thread.currentThread().getName()
                    //        + " thread yield! And now the i = " + i);
                }
                System.out.println("I am " + Thread.currentThread().getName()
                        + " thread and now the i = " + i--);
            }

        }
    }

    public static void main(String[] args) {
        MyThread myThreadA = new MyThread("AAA");
        MyThread myThreadB = new MyThread("BBB");
        MyThread myThreadC = new MyThread("CCC");

        //设置优先级，发挥作用由 cpu 决定，因此不一定有效，由调度程序决定哪一个线程被执行
        myThreadA.setPriority(Thread.MAX_PRIORITY); //从1到10的范围指定。10最高优先级，1最低优先级，5普通优先级(默认值)。
        myThreadB.setPriority(Thread.MIN_PRIORITY);
        myThreadC.setPriority(Thread.NORM_PRIORITY);

        myThreadA.start();
        myThreadB.start();
        try {
            myThreadB.join(); //等待 myThreadB 执行完成才会执行下面的代码，否则myThreadC 一直被阻塞
            // 由于 myThreadA 先于 myThreadB start(), 并不影响 myThreadA 的执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myThreadC.start();

    }
}
