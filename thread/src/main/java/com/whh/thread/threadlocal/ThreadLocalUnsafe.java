package com.whh.thread.threadlocal;

import com.whh.thread.SleepToos;

/**
 * 类说明：ThreadLocal的线程不安全演示
 * 错误使用 ThreadLocal 导致线程不安全
 * 5 个线程中保存的是同一 Number 对象的引用，当线程睡眠，其他线程将 num 变量进行了修改，
 * 而修改的对象 Number 的实例是同一份，因此它们最终输出的结果是相同的。
 *
 * author:wuhuihui 2021.07.07
 */
public class ThreadLocalUnsafe implements Runnable {

    public Number number = new Number(0);
    public static ThreadLocal<Number> value = new ThreadLocal<Number>() {
    };

    public void run() {
        //每个线程计数加一
        number.setNum(number.getNum() + 1);
        //将其存储到ThreadLocal中
        value.set(number);
        SleepToos.sleep(2);
        //输出num值
        System.out.println(Thread.currentThread().getName() + "=" + value.get().getNum());
    }


    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new ThreadLocalUnsafe()).start();
        }
    }

    private static class Number {
        public Number(int num) {
            this.num = num;
        }

        private int num;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        @Override
        public String toString() {
            return "Number [num=" + num + "]";
        }
    }

}
