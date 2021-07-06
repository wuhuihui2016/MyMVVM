package com.whh.thread;

/**
 * 线程 sleep 工具
 * author:wuhuihui 2021.06.30
 */
public class SleepToos {

    public static void sleep(int second) {
        try {
            Thread.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
