package com.whh.thread.thread;

/**
 * 论如何中断线程
 * interrupt() 只是给线程打个招呼，可以停止了，并不会立即停止，将 isInterrupted 设为 true, 不影响线程状态
 * 线程时协作式，不是抢占式，不会强制终结
 * 不建议自定义中断线程标志位，如：isStop(),当需要暂停线程时使用 interrupt()，是否被暂时使用 isInterrupted() 方法
 * 原因：run 方法里有阻塞调，可能无法很快检测到中断标志，线程必须从阻塞调用返回后，才会检查这个中断标志，使用 interrupt() 方法更好
 * interrupt(); //将 isInterrupted() 设为 true，该方法不会影响线程状态，线程不会进入就绪
 * interrupted(); //将 isInterrupted() 设为 flase
 * isInterrupted(); //返回当前 isInterrupted() 的结果，boolean 类型
 * <p>
 * 处于死锁状态时，不会理会中断，即处于死锁状态的线程无法被中断
 * <p>
 * 停止线程：不建议使用 stop()，会破坏线程的数据完整性，方法已弃用，同时该方法不安全，不能保证资源及时释放，容易导致死锁问题
 * <p>
 *
 * sleep()方法没有释放锁，仅仅释放了 CPU 资源或者让当前线程停止执行一段时间
 *
 * 线程调用sleep()、join()、wait()方法会检查isInterrupted()，如果为 true, 则抛出中断异常，并将 isInterrupted() 清除，设为 false
 *
 * author:wuhuihui 2021.06.30
 */
public class InterruptThread {

    static class MyThread extends Thread {

        int i = 10;

        @Override
        public void run() {
            super.run();
            setName("MyThread");
            while (i > 0) {
                if (i == 5) interrupt(); //打个招呼
                if (isInterrupted()) {
                    System.out.println(Thread.currentThread().getName()
                            + " thread is isInterrupted! and now the i = " + i--);
//                    Thread.currentThread().interrupt(); // 清除中断状态，isInterrupted() 即为 true
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    //如果此处捕获到中断异常，会将 isInterrupted() 设为 false
                    //JDK 这么处理的原因：避免线程中断后不能及时释放资源
                    e.printStackTrace();
                    System.out.println(Thread.currentThread().getName()
                            + " thread is isInterrupted ? " + isInterrupted());
                }
                System.out.println(Thread.currentThread().getName()
                        + " thread and now the i = " + i--);
            }

        }
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();
    }

}
