package com.whh.others.abstracts;

/**
 * 抽象类
 * author:wuhuihui 2021.07.22
 */
public abstract class Instrument {
    public abstract void play();

    public static void start() {
        System.out.println("Instrument start");
    }
}
