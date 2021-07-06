package com.whh.thread.cas;

import com.whh.thread.SleepToos;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 解决ABA问题：解决办法：在每次修改时加版本戳
 * AtomicMarkableReference：关心变量是否被修改
 * AtomicStampedReference：关心变量是否被修改和修改次数
 *
 * compareAndSet 如果当前状态值等于预期值，则以原子方式将同步状态设置为给定的更新值。
 * <p>
 * author:wuhuihui 2021.07.05
 */
public class UseAtomicStampedReference {

    static AtomicStampedReference<String> asr
            = new AtomicStampedReference("whh", 0);

    public static void main(String[] args) throws InterruptedException {
        final int oldStamp = asr.getStamp(); //拿到当前版本
        final String oldReference = asr.getReference();
        System.out.println(oldStamp + "=======" + oldReference);

        Thread rightStampThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()
                        + ", oldReference: " + oldReference
                        + ", oldStamp: " + oldStamp
                        + ", compareAndSet: " + asr.compareAndSet(oldReference, oldReference + "123", oldStamp, oldStamp + 1)
                );
            }
        });
//
        Thread errorStampThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()
                        + ", oldReference: " + oldReference
                        + ", oldStamp: " + oldStamp
                        + ", compareAndSet: " + asr.compareAndSet(oldReference, oldReference + "error", oldStamp, oldStamp + 1)
                );
            }
        });

//        rightStampThread.start();
//        rightStampThread.join();
//
//        errorStampThread.start();
//        errorStampThread.join();

        /**
         * 输出结果：
         * 0=======whh
         * Thread-0, oldReference: whh, oldStamp: 0, compareAndSet: true
         * Thread-1, oldReference: whh, oldStamp: 0, compareAndSet: false
         * whh123=======1
         **/

        System.out.println("first, oldReference: " + oldReference
                + ", oldStamp: " + oldStamp
                + ", compareAndSet: " + asr.compareAndSet(oldReference, oldReference + "whh", oldStamp, oldStamp + 1)
        );

        //在第二次 compareAndSet 失败，原因：当前状态值已经被更改，则不再更改
        System.out.println("second, oldReference: " + oldReference
                + ", oldStamp: " + oldStamp
                + ", compareAndSet: " + asr.compareAndSet(oldReference, oldReference + "whh2", oldStamp, oldStamp + 1)
        );

        System.out.println(asr.getReference() + "=======" + asr.getStamp());

        /**
         * 输出结果：
         * 0=======whh
         * first, oldReference: whh, oldStamp: 0, compareAndSet: true
         * second, oldReference: whh, oldStamp: 0, compareAndSet: false
         * whhwhh=======1
         **/


    }
}
